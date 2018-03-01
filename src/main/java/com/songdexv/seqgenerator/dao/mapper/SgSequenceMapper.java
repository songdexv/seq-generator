package com.songdexv.seqgenerator.dao.mapper;

import com.songdexv.seqgenerator.dao.entity.SgSequence;
import com.songdexv.seqgenerator.dao.entity.SgSequenceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SgSequenceMapper {
    int countByExample(SgSequenceExample example);

    int deleteByExample(SgSequenceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SgSequence record);

    int insertSelective(SgSequence record);

    List<SgSequence> selectByExample(SgSequenceExample example);

    SgSequence selectByPrimaryKey(Long id);

    List<SgSequence> selectByExampleWithPaging(SgSequenceExample example);

    int updateByExampleSelective(@Param("record") SgSequence record, @Param("example") SgSequenceExample example);

    int updateByExample(@Param("record") SgSequence record, @Param("example") SgSequenceExample example);

    int updateByPrimaryKeySelective(SgSequence record);

    int updateByPrimaryKey(SgSequence record);

    List<SgSequence> selectByExampleForUpdate(SgSequenceExample example);

    SgSequence selectByPrimaryKeyForUpdate(Long id);

    int insertBatch(List<SgSequence> record);
}