package org.jeecg.modules.demo.test.mapper;

import java.util.List;
import org.jeecg.modules.demo.test.entity.ExaminationTitle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: examination_title
 * @Author: jeecg-boot
 * @Date:   2023-11-07
 * @Version: V1.0
 */
public interface ExaminationTitleMapper extends BaseMapper<ExaminationTitle> {

	/**
	 * 通过主表id删除子表数据
	 *
	 * @param mainId 主表id
	 * @return boolean
	 */
	public boolean deleteByMainId(@Param("mainId") String mainId);

  /**
   * 通过主表id查询子表数据
   *
   * @param mainId 主表id
   * @return List<ExaminationTitle>
   */
	public List<ExaminationTitle> selectByMainId(@Param("mainId") String mainId);
}
