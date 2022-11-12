package com.clm.counselor.service;

import com.clm.counselor.entity.DailyWork;
import com.baomidou.mybatisplus.extension.service.IService;
import com.clm.counselor.entity.Work;

import java.util.List;

/**
 * <p>
 * 工作表 服务类
 * </p>
 *
 * @author su
 * @since 2021-12-09
 */
public interface DailyWorkService extends IService<DailyWork> {

    List<DailyWork> queryFinishDailyWork(String userId);

    List<DailyWork> queryProvisional(String userId);

    DailyWork addProvisional(Work work);

    DailyWork editProvisional(Work work);

    DailyWork finishDailyWork(String dailyWorkId);

    DailyWork revokeFinishDailyWork(String dailyWorkId);

}
