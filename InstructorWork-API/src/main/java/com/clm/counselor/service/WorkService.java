package com.clm.counselor.service;

import com.clm.counselor.entity.Work;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 日常工作表 服务类
 * </p>
 *
 * @author su
 * @since 2021-12-09
 */
public interface WorkService extends IService<Work> {

    Work addWork(Work work);

    Work editWork(Work work);

    boolean deleteWork(String workId);

    List<Work> queryWorkNotFinish(String userId);

}
