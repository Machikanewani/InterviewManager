package com.example.InterviewManager.domain;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface InterviewRepository {
    @Select("select * from companies")
    List<CompanyEntity> findAllCompanies();

    @Select("select * from flowBlocks where whichCompanyId = #{companyId} order by id")
    List<BlockEntity> findAllBlocksByCompany(long companyId);

    @Insert("insert into companies (name, link) values (#{companyName}, #{link})")
    void insertCompany(String companyName, String link);

    @Select("select id from companies where name = #{companyName}")
    long getCompanyId(String companyName);

    @Insert("insert into flowBlocks (whichCompanyId, blockName, date, memo) " +
            "values (#{whichCompanyId}, #{blockName}, #{date}, #{memo})")
    void insertBlock(long whichCompanyId, String blockName, String date, String memo);

    @Select("select * from companies where id = #{companyId}")
    CompanyEntity findCompanyById(long companyId);

    @Update("update companies set name = #{companyName}, link = #{link} where id = #{companyId}")
    void updateCompany(long companyId, String companyName, String link);

    @Update("update flowBlocks set whichCompanyId = #{whichCompanyId}, blockName = #{blockName}," +
            "date = #{date}, memo = #{memo} where id = #{id}")
    void updateBlock(long id, long whichCompanyId, String blockName, String date, String memo);

    @Delete("delete from flowBlocks where whichCompanyId = #{companyId}")
    void deleteAllBlocks(long companyId);

    @Delete("delete from companies where id = #{companyId}")
    void deleteCompany(long companyId);

    @Delete("delete from flowBlocks where id = #{blockId}")
    void deleteBlock(long blockId);
}
