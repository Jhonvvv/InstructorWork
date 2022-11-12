package com.clm.system.service;

import com.clm.system.entity.SysDepart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.clm.system.model.DepartTree;

import java.util.List;

/**
 * <p>
 * 组织机构表 服务类
 * </p>
 *
 * @author su
 * @since 2021-11-22
 */
public interface SysDepartService extends IService<SysDepart> {
    boolean deleteDepart(String departId);
    boolean deleteBatchDepart(String[] departIds);
    SysDepart queryUserDepart(String userId);
    List<DepartTree> queryTreeList();
}
