package wx.milk.web.base.inspect;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于在应用内及应用间保存、传递执行上下文信息。
 *
 * @author LIU Fangran
 */
public class ExecutionContext {
    /**
     * 用于保存线程相关信息
     */
    transient static ThreadLocal<Map<String, String>> threadLocal = new ThreadLocal<Map<String, String>>();

    /**
     * 应用代码。注意该值不在应用间传递
     */
    static String appCode = "defaultAppCode";

    /**
     * 用户id 键
     */
    public static final String USER_ID = "user_id";
    /**
     * 用户名 键
     */
    public static final String USER_NAME = "user_name";

    /**
     * 公司代码 键
     */
    public static final String CORP_CODE = "corp_code";

    /**
     * 额外字段，用于数据扩展 键
     */
    public static final String EXTRA_FIELDS = "extra_fields";

    /**
     * 客户端IP 键
     */
    public static final String CLIENT_IP = "client_ip";

    /**
     * 角色ID
     */
    public static final String ROLE_ID = "role_id";

    /**
     * Note, it's using login_role_code as ROLE_CODE values, since sessionMap
     * uses it as the key.
     */
    public static final String ROLE_CODE = "login_role_code";

    /**
     * 地域 键
     */
    public static final String LOCALE = "locale";

    public static final String TRANSLATION_MODE = "translationMode";

    public static final String REQUEST_SERVER_NAME = "request_server_name";

    public static final String METHOD_STACK_TRACE = "method_stack_trace";

    /**
     * 管理员 键
     */
    public static final String ADMIN = "admin";

    public static final String HAS_ADMIN = "has_admin";

    public static final String ADMIN_ROLE = "adminRole";

    public static final String TRAINER_ROLE = "trainerRole";

    /**
     * （注意，extraFields 不是从session中取得，而是从request中取得） Note, extraFields is not
     * loaded from session.
     */
    public static String DEFAULT_KEYS_LOAD_FROM_SESSION_MAP = USER_ID + ","
            + USER_NAME + "," + CORP_CODE + "," + LOCALE + "," + CLIENT_IP
            + "," + ADMIN + "," + HAS_ADMIN + "," + ROLE_ID + "," + ROLE_CODE
            + "," + ADMIN_ROLE + "," + TRAINER_ROLE;


    // For Open
    public static final String DATA_ACCOUNT_NAME = "data_account_name";
    public static final String SECRET = "secret";
    public static final String TIMESTAMP = "timestamp";
    public static final String SIGN = "sign";

    /**
     * 构造函数
     */
    public ExecutionContext() {
        // For Spring initialization.
    }

    public ExecutionContext(String appCode) {
        ExecutionContext.setAppCode(appCode);
    }

    /**
     * 从 ThreadLocal中获取名值Map(不包含appCode)
     *
     * @return 名值Map
     */
    public static Map<String, String> getContextMap() {
        return threadLocal.get();
    }

    /**
     * 从 ThreadLocal 获取名值Map
     *
     * @param contextMap 名值Map
     */
    public static void setContextMap(Map<String, String> contextMap) {
        threadLocal.set(contextMap);
    }

    public static String getAppCode() {
        return appCode;
    }

    public static void setAppCode(String appCode) {
        ExecutionContext.appCode = appCode;
    }

    /**
     * （获取键下的值.如果不存在，返回null；如果名值Map未初始化，也返回null） Get the value of key. Would
     * return null if context map hasn't been initialized.
     *
     * @param key 键
     * @return 键下的值
     */
    public static String get(String key) {
        Map<String, String> contextMap = getContextMap();
        if (contextMap == null) {
            return null;
        }

        return contextMap.get(key);
    }

    /**
     * （设置名值对。如果Map之前为null，则会被初始化） Put the key-value into the context map;
     * <p/>
     * Initialize the map if the it doesn't exist.
     *
     * @param key   键
     * @param value 值
     * @return 之前的值
     */
    public static String put(String key, String value) {
        Map<String, String> contextMap = getContextMap();
        if (contextMap == null) {
            contextMap = new HashMap<String, String>();
            setContextMap(contextMap);
        }

        return contextMap.put(key, value);
    }

    public static String getUserId() {
        return get(USER_ID);
    }

    public static void setUserId(String userId) {
        put(USER_ID, userId);
    }

    public static String getUserName() {
        return get(USER_NAME);
    }

    public static void setUserName(String userName) {
        put(USER_NAME, userName);
    }

    public static String getCorpCode() {
        return get(CORP_CODE);
    }

    public static void setCorpCode(String corpCode) {
        put(CORP_CODE, corpCode);
    }

    public static String getExtraFields() {
        return get(EXTRA_FIELDS);
    }

    public static void setExtraFields(String extraFields) {
        put(EXTRA_FIELDS, extraFields);
    }

    public static String getLocale() {
        return get(LOCALE);
    }

    public static void setLocale(String locale) {
        put(LOCALE, locale);
    }

    public static String getClientIp() {
        return get(CLIENT_IP);
    }

    public static void setClientIp(String clientIp) {
        put(CLIENT_IP, clientIp);
    }

    public static String getMethodStackTrace() {
        return get(METHOD_STACK_TRACE);
    }

    public static void setMethodStackTrace(String methodStackTrace) {
        put(METHOD_STACK_TRACE, methodStackTrace);
    }

    public static Boolean isTranslationMode() {
        String value = get(TRANSLATION_MODE);
        return value != null && Boolean.parseBoolean(value);

    }

    public static Boolean isAdmin() {
        String value = get(ADMIN);
        return value != null && Boolean.parseBoolean(value);
    }

    public static Boolean isHasAdmin() {
        String value = get(HAS_ADMIN);
        return value != null && Boolean.parseBoolean(value);
    }

    public static void setAdmin(Boolean admin) {
        if (admin == null) {
            put(ADMIN, "false");
        } else {
            put(ADMIN, admin.toString());
        }
    }

    public static String getRequestServerName() {
        return get(REQUEST_SERVER_NAME);
    }

    public static void setRequestServerName(String requestServerName) {
        put(REQUEST_SERVER_NAME, requestServerName);
    }

    public static String getRoleCode() {
        return get(ROLE_CODE);
    }

    public static void setRoleCode(String requestDomain) {
        put(ROLE_CODE, requestDomain);
    }

    public static String getRoleId() {
        return get(ROLE_ID);
    }

    public static void setRoleId(String requestDomain) {
        put(ROLE_ID, requestDomain);
    }

    public static String getAdminRole() {
        return get(ADMIN_ROLE);
    }

    public static boolean isAdminRole() {
        String value = get(ADMIN_ROLE);
        return value != null && Boolean.parseBoolean(value);
    }

    public static void setAdminRole(String adminRole) {
        put(ADMIN_ROLE, adminRole);
    }

    public static String getTrainerRole() {
        return get(TRAINER_ROLE);
    }

    public static boolean isTrainerRole() {
        String value = get(TRAINER_ROLE);
        return value != null && Boolean.parseBoolean(value);
    }

    public static void setTrainerRole(String trainerRole) {
        put(TRAINER_ROLE, trainerRole);
    }
}
