package com.example.InterviewManager.domain;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface InterviewRepository {
    @Select("select * from companies where userId = #{userId}")
    List<CompanyEntity> findAllCompanies(long userId);

    @Select("select * from flowBlocks where whichCompanyId = #{companyId} order by id")
    List<BlockEntity> findAllBlocksByCompany(long companyId);

    @Select("select * from flowBlocks where id = #{blockId}")
    BlockEntity findBlockById(long blockId);

    @Select("select id from flowBlocks where whichCompanyId = #{companyId} and blockName = #{blockName}")
    long getBlockId(long companyId, String blockName);

    @Insert("insert into companies (userId, name, link) values (#{userId}, #{companyName}, #{link})")
    void insertCompany(Long userId, String companyName, String link);

    @Select("select id from companies where name = #{companyName}")
    long getCompanyId(String companyName);

    @Insert("insert into flowBlocks (whichCompanyId, blockName, dateTime, memo) " +
            "values (#{whichCompanyId}, #{blockName}, #{dateTime}, #{memo})")
    void insertBlock(long whichCompanyId, String blockName, String dateTime, String memo);

    @Select("select * from companies where id = #{companyId}")
    CompanyEntity findCompanyById(long companyId);

    @Update("update companies set name = #{companyName}, link = #{link} where id = #{companyId}")
    void updateCompany(long companyId, String companyName, String link);

    @Update("update flowBlocks set whichCompanyId = #{whichCompanyId}, blockName = #{blockName}," +
            "dateTime = #{dateTime}, memo = #{memo} where id = #{id}")
    void updateBlock(long id, long whichCompanyId, String blockName, String dateTime, String memo);

    @Delete("delete from flowBlocks where whichCompanyId = #{companyId}")
    void deleteAllBlocks(long companyId);

    @Delete("delete from companies where id = #{companyId}")
    void deleteCompany(long companyId);

    @Delete("delete from flowBlocks where id = #{blockId}")
    void deleteBlock(long blockId);

    @Select("select id from users where email = #{email}")
    Long getUserIdByEmail(String email);

    @Insert("insert into users (email, iconURL) values (#{email}, #{picture})")
    void insertUser(String email, String picture);
}
