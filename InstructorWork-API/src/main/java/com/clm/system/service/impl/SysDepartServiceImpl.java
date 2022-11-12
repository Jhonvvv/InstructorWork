package com.clm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.clm.common.base.constant.CommonConstant;
import com.clm.system.entity.SysDepart;
import com.clm.system.entity.SysUserDepart;
import com.clm.system.mapper.SysDepartMapper;
import com.clm.system.mapper.SysUserDepartMapper;
import com.clm.system.model.DepartTree;
import com.clm.system.service.SysDepartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 组织机构表 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-11-22
 */
@Service
public class SysDepartServiceImpl extends ServiceImpl<SysDepartMapper, SysDepart> implements SysDepartService {

    @Autowired
    private SysDepartMapper sysDepartMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteDepart(String departId) {
        //1.删除用户和部门关系
        sysDepartMapper.deleteDepartUserRelation(departId);
        this.removeById(departId);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatchDepart(String[] departIds) {
        //1.删除用户和部门关系
        sysDepartMapper.deleteBathDepartUserRelation(departIds);
        this.removeByIds(Arrays.asList(departIds));
        return false;
    }

    @Override
    public SysDepart queryUserDepart(String userId) {
        SysDepart sysDepart = baseMapper.queryUserDepart(userId);
        return sysDepart;
    }

    @Override
    public List<DepartTree> queryTreeList() {
        List<SysDepart> sysDepartList = baseMapper.selectList(null);
        List<DepartTree> treeList = getTreeList(new ArrayList<DepartTree>(), sysDepartList, null);
        return treeList;
    }

    private List<DepartTree> getTreeList(List<DepartTree> departTreeList, List<SysDepart> sysDepartList, DepartTree temp) {
        for (SysDepart sysDepart : sysDepartList) {
            String parentId = sysDepart.getParentId();
            DepartTree tree = new DepartTree(sysDepart);
            if (temp == null && StringUtils.isEmpty(parentId)) {
                departTreeList.add(tree);
                getTreeList(departTreeList, sysDepartList, tree);
            } else if (temp != null && parentId != null && parentId.equals(temp.getId())) {
                temp.getChildren().add(tree);
                getTreeList(departTreeList, sysDepartList, tree);

            }
        }
        return departTreeList;
    }


}
