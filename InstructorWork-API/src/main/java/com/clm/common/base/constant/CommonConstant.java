package com.clm.common.base.constant;

/**
 * @Author su
 * @Date 2021/11/24 19:42
 */
public interface CommonConstant {

    /**
     *  审核状态（0，待审核， 1，审核中 2，通过 3，拒绝）
     */
    public static final Integer APPROVE_STATUS_0=0;
    public static final Integer APPROVE_STATUS_1=1;
    public static final Integer APPROVE_STATUS_2=2;
    public static final Integer APPROVE_STATUS_3=3;

    /**
     *  处罚申请类型
     */
    public static final Integer PUNISHMENT_TYPE_0=0;
    public static final Integer PUNISHMENT_TYPE_1=1;


    /**
     * 文件上传类型 本地上传
     */
    public static final String UPLOAD_TYPE= "local";

    /**
     * 默认密码
     */
    public static final String DEFAULT_PASSWORD="123456";

    /**
     * 0：一级菜单  1：子菜单
     */
    public static final Integer MENU_TYPE_0 = 0;
    public static final Integer MENU_TYPE_1 = 1;

    /**
     * 1:一级部门 2:子部门
     */
    public static final Integer ORG_TYPE_1 = 1;
    public static final Integer ORG_TYPE_2 = 2;

    /**
     * 是否叶子节点:    1:是   0:不是
     */
    public static final Integer LEAF_TYPE_0 = 0;
    public static final Integer LEAF_TYPE_1 = 1;

    /**
     * 是否用户已被冻结 1正常(解冻) 2冻结
     */
    public static final Integer USER_UNFREEZE = 1;
    public static final Integer USER_FREEZE = 2;

    /**
     * 用户身份 （1:学生  2:辅导员 3:管理员 4:其他）
     */
    public static final Integer USER_IDENTITY_1 = 1;
    public static final Integer USER_IDENTITY_2 = 2;
    public static final Integer USER_IDENTITY_3 = 3;
    public static final Integer USER_IDENTITY_4 = 4;

    /**
     * 角色 （admin:学生  student:辅导员 counselor:管理员 ）
     */
    public static final String ROLE_CODE_ADMIN = "admin";
    public static final String ROLE_CODE_STUDENT= "student";
    public static final String ROLE_CODE_COUNSELOR= "counselor";

    /**
     * Token缓存时间：3600秒即一小时
     */
    public static final int TOKEN_EXPIRE_TIME = 3600;
    /**
     * 登录用户Token令牌缓存KEY前缀
     */
    public static final String PREFIX_USER_TOKEN = "prefix_user_token_";

    /**
     * 前端token key
     */
    public static final String CLM_TOKEN="clm_token";

    /**
     * 处罚撤销(0:正常,1:撤销)
     */
    public static final int  PUNISHMENT_REVOKE_0=0;
    public static final int  PUNISHMENT_REVOKE_1=1;

    /**
     * 工作类型(0:工作,1:会议)
     */
    public static final int WOKE_TYPE_0=0;
    public static final int WOKE_TYPE_1=1;

    /**
     *  工作状态(0:未完成,1:完成)
     */
    public static final int Finish_0=0;
    public static final int Finish_1=1;

    /**
     *  工作性质(0:日常,1:临时)
     */
    public static final int PROVISIONAL_0=0;
    public static final int PROVISIONAL_1=1;

    /**
     *  一天的开始
     */
    public static final String START_DAY="00:00";

    /**
     * 一天的结束
     */
    public static final String END_DAY="23:59";
}
