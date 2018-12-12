package wx.milk.manager.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import wx.base.UUID;
import wx.base.manager.IEntryResultHandler;
import wx.base.manager.IManager;
import wx.base.service.IService;
import wx.exception.manager.ManagerException;

import wx.query.Pagenation;
import wx.query.Query;
import wx.security.BaseEntity;
import wx.security.IEntity;
import wx.security.User;
import wx.util.Helper;
import wx.util.UUIDUtils;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;


public abstract class BaseManager<T extends IEntity, K> implements IManager<T, K> {protected Log logger = LogFactory.getLog(getClass());
    protected final Class<?> entryClass;
    protected final Class<?> idClass;
    protected final Field noField;


    public BaseManager() {
        // manager类在spring中只会初始化一次
        entryClass = Helper.getSuperClassGenricType(this.getClass(), 0);
        idClass = Helper.getSuperClassGenricType(this.getClass(), 1);
        this.noField = getNoProperty();

    }

    protected Query preHandleQuery(Query query){
        return query;
    }

    protected Field getNoProperty() {
        String name = entryClass.getSimpleName() + "No";
        name = name.substring(0, 1).toLowerCase() + name.substring(1);
        Field f = ReflectionUtils.findField(entryClass, name);
        if (f != null) {
            ReflectionUtils.makeAccessible(f);
        }
        return f;
    }

    @SuppressWarnings("unchecked")
    protected K generateId() {
        return (K) UUID.newUUID().toString();
    }

    /**
     * 生成序列
     *
     * @param entry
     * @return
     */
    protected String generateCoding(T entry) {
        return null;
    }

    /**
     * 设置序列
     *
     * @param entry
     */
    protected void setEntryNo(T entry) {
        if (noField == null) {
            return;
        }

        try {
            Object v = noField.get(entry);
            if (v == null || "".equals(v)) {
                noField.set(entry, generateCoding(entry));
            }
        } catch (Exception e) {
            throw new ManagerException(e);
        }
    }

    protected abstract IService<T, K> getService();

    @Override
    public T findByPrimaryKey(K id) throws ManagerException {
        try {
            return getService().findByPrimaryKey(id);
        } catch (Exception e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public T findByParam(Query query) throws ManagerException {
        try {
            return getService().findByParam(preHandleQuery(query));
        } catch (Exception e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public Integer selectCount(Query query) throws ManagerException {
        try {
            return getService().selectCount(preHandleQuery(query));
        } catch (Exception e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public List<T> selectByPage(Query query, Pagenation page) throws ManagerException {
        try {
            return getService().selectByPage(preHandleQuery(query), page);
        } catch (Exception e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public List<T> selectByParams(Query query) throws ManagerException {
        try {
            return getService().selectByParams(preHandleQuery(query));
        } catch (Exception e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public void selectByParams(Query query, IEntryResultHandler<T> handler) throws ManagerException {
        try {
            getService().selectByParams(preHandleQuery(query), handler);
        } catch (Exception e) {
            throw new ManagerException(e);
        }
    }

    @SuppressWarnings({ "unchecked" })
    protected void initEntry(T entry) {
        BaseEntity<K> item = (BaseEntity<K>) entry;
        Date d = new Date();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user != null && item.getCreateTime() == null) {
            item.setCreateTime(d);
            item.setCreateUser(user.getName());
            item.setUpdateTime(d);
            item.setUpdateUser(item.getCreateUser());
            item.setId((K)UUIDUtils.getUUID());
        }
        if (idClass != null && idClass.getSimpleName().equals("String")
                && (item.getId() == null || item.getId().toString() == "")) {
            item.setId(generateId());
        }
    }

    @Override
    public Integer insert(T entry) throws ManagerException {
        initEntry(entry);
        try {
            return getService().insert(entry);
        } catch (Exception e) {
            throw new ManagerException(e);
        }
    }

    @Override
    @SuppressWarnings({ "unchecked" })
    public Integer update(T entry) throws ManagerException {
        try {
            BaseEntity<K> item = (BaseEntity<K>) entry;
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            if( user != null ) {
                item.setUpdateUser(user.getName());
            }
            item.setUpdateTime(new Date());
            return getService().update(entry);
        } catch (Exception e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public Integer deleteByParams(Query query) throws ManagerException {
        try {
            return getService().deleteByParams(query);
        } catch (Exception e) {
            throw new ManagerException(e);
        }
    }

    @Override
    public Integer deleteByPrimaryKey(K id) throws ManagerException {
        try {
            return getService().deleteByPrimaryKey(id);
        } catch (Exception e) {
            throw new ManagerException(e);
        }
    }

    public Integer delete(T t) throws ManagerException {
        try {
            @SuppressWarnings("unchecked")
            BaseEntity<K> entry = (BaseEntity<K>) t;
            if (entry.getId() != null && !"".equals(entry.getId())) {
                return this.deleteByPrimaryKey(entry.getId());
            }
            return 0;
        } catch (Exception e) {
            throw new ManagerException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
    public Integer batchSave(List<T> inserted, List<T> updated, List<T> deleted) throws ManagerException {
        try {

            if (inserted != null) {
                for (T t : inserted) {
                    this.insert(t);
                }
            }

            if (updated != null) {
                for (T t : updated) {
                    this.update(t);
                }
            }

            if (deleted != null) {

                for (T t : deleted) {
                    this.delete(t);
                }
            }
            return 1;
        } catch (Exception e) {
            throw new ManagerException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int batchDeleteByParams(Query query){
        int delCount=0;
        List<T> selectResult = selectByParams(query);
//		if(BaseEntity.class.isAssignableFrom(entryClass)){
        for (T t : selectResult) {
            delCount+=deleteByPrimaryKey(((BaseEntity<K>)t).getId());
        }
//		}
        return delCount;
    }

    @Override
    public Integer validate(Query query) throws ManagerException {
        try {
            return getService().validate(preHandleQuery(query));
        } catch (Exception e) {
            throw new ManagerException(e);
        }
    }
}
