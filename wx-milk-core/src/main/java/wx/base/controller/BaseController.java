package wx.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import wx.annotation.JsonVariable;
import wx.base.domain.DataChangeEntry;
import wx.base.excel.EntryResultHandler;
import wx.base.excel.ExcelColumn;
import wx.base.excel.ExcelExport;
import wx.base.manager.IManager;
import wx.exception.JsonManagerException;
import wx.exception.manager.ManagerException;
import wx.query.PageResult;
import wx.query.Pagenation;
import wx.query.Query;
import wx.security.IEntity;
import wx.util.Helper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;


/**
 * 
 * 控制层基础类
 * 
 * @author zhonghui
 *
 * @param <T>
 * @param <K>
 */
public abstract class BaseController<T extends IEntity, K> extends AbstractController implements IController<T, K> {

	protected abstract IManager<T, K> getManager();

	private Class<?> persistentClass;
	@Autowired
	protected Environment env;

	public Class<?> getPersistentClass() {
		return this.persistentClass;
	}

	public BaseController() {
		this.persistentClass = (Class<?>) Helper.getSuperClassGenricType(getClass(), 0);
		Assert.notNull(persistentClass);
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@Override
	public T findByPrimaryKey(@PathVariable K id) {
		return getManager().findByPrimaryKey(id);
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/get")
	@Override
	public T findByParam(Query query) throws JsonManagerException {
		return getManager().findByParam(query);
	}

	@ResponseBody
	@RequestMapping("/list")
	@Override
	public PageResult<T> selectByPage(Query query, Pagenation page) {

		long total = page.getTotal();
		if (total <= 0) {
			total = getManager().selectCount(query);
		}
		List<T> rows = getManager().selectByPage(query, page);
		return new PageResult<T>(rows, total);
	}

	@ResponseBody
	@RequestMapping("/query")
	@Override
	public List<T> selectByParams(Query query) {
		return getManager().selectByParams(query);

	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/create")
	@Override
	public T create(T entry) {
		getManager().insert(entry);
		return entry;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/update")
	@Override
	public T update(T entry) throws JsonManagerException {
		try {
			getManager().update(entry);
			return entry;
		} catch (ManagerException e) {
			throw new JsonManagerException(e);
		}
	}

	/**
	 * user/delete?account=admin
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/delete")
	@Override
	public Integer deleteByParams(Query query) {
		return getManager().deleteByParams(query);
	}

	/**
	 * user/delete/1
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/delete/{id}")
	@Override
	public Integer deleteByPrimaryKey(@PathVariable("id") K id) {
		return getManager().deleteByPrimaryKey(id);
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/batchsave")
	public Integer batchSave(DataChangeEntry<T> datas) {
		return getManager().batchSave(datas.getInserted(), datas.getUpdated(), datas.getDeleted());
	}

	protected EntryResultHandler<T> entryResultHandler;

	@RequestMapping(value = "export", method = RequestMethod.POST)
	@ResponseBody
	public void export(Query query, @RequestParam("_fileName") String fileName,
					   @JsonVariable("_columns") ExcelColumn[][] columns, HttpServletResponse response) {
		EntryResultHandler<T> handler = null;
		try (final ExcelExport excel = new ExcelExport(columns)) {

			handler = getExportResultHandler();

			handler.setExcel(excel);

			excel.open(fileName);

			getManager().selectByParams(query, handler);

			OutputStream stream = getOutputFileStream(fileName, response);

			excel.flush(stream);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (handler != null)
				handler.close();
		}
	}

	protected synchronized EntryResultHandler<T> getExportResultHandler() {
		if (entryResultHandler == null) {
			entryResultHandler = new EntryResultHandler<T>(true, this.getPersistentClass()) {
			};
		}
		return entryResultHandler;
	}

	protected OutputStream getOutputFileStream(String fileName, HttpServletResponse response) throws IOException {

		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

		String name = new String(fileName.getBytes("gb2312"), "iso-8859-1");
		// 文件名
		response.setHeader("Content-Disposition", "attachment;filename=" + name + ".xlsx");

		response.setHeader("Pragma", "no-cache");

		return response.getOutputStream();
	}

}
