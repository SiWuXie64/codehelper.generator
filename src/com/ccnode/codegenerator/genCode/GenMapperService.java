package com.ccnode.codegenerator.genCode;

import com.ccnode.codegenerator.enums.FileType;
import com.ccnode.codegenerator.function.EqualCondition;
import com.ccnode.codegenerator.function.MapperCondition;
import com.ccnode.codegenerator.pojo.*;
import com.ccnode.codegenerator.pojoHelper.GenCodeResponseHelper;
import com.ccnode.codegenerator.util.GenCodeUtil;
import com.ccnode.codegenerator.util.RegexUtil;
import com.ccnode.codegenerator.util.ReplaceUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

import static com.ccnode.codegenerator.util.GenCodeUtil.*;

/**
 * What always stop you is what you always believe.
 * <p>
 * Created by zhengjun.du on 2016/05/28 21:14
 */
public class GenMapperService {

    private static String COMMA = ",";

    public static void genMapper( GenCodeResponse response) {
        for (OnePojoInfo pojoInfo : response.getPojoInfos()) {
            GeneratedFile fileInfo = GenCodeResponseHelper.getByFileType(pojoInfo, FileType.MAPPER);
            Boolean expand = response.getUserConfigMap().get("mapper.expand").equals("true");
            genMapper(pojoInfo,fileInfo,expand);
        }
    }

    private static void genMapper(OnePojoInfo onePojoInfo, GeneratedFile fileInfo, Boolean expand) {
        String pojoName = onePojoInfo.getPojoName();
        List<String> oldLines = fileInfo.getOldLines();

        ListInfo<String> listInfo = new ListInfo<String>();
        if(oldLines.isEmpty()){
            listInfo.setFullList(getMapperHeader(onePojoInfo));
        }else{
            listInfo.setFullList(oldLines);
        }

        Pair<Integer, Integer> posPair = ReplaceUtil
                .getPos(listInfo.getFullList(), "<resultMap id=\"BaseResultMap\" type=", "</resultMap>", new MapperCondition());
        listInfo.setPos(posPair);
        listInfo.setNewSegments(genBaseResultMap(onePojoInfo));
        ReplaceUtil.merge(listInfo, new EqualCondition<String>() {
            @Override
            public boolean isEqual(String o1, String o2) {
                String match1 = RegexUtil.getMatch("result(.*)property", o1);
                String match2 = RegexUtil.getMatch("result(.*)property", o2);
                if(StringUtils.isBlank(match1) ){
                    return false;
                }
                return match1.equals(match2);
            }
        });

        posPair = ReplaceUtil
                .getPos(listInfo.getFullList(), "<sql id=\"all_column\">", "</sql>", new MapperCondition());
        listInfo.setPos(posPair);
        listInfo.setNewSegments(genAllColumn(onePojoInfo));
        ReplaceUtil.merge(listInfo, new EqualCondition<String>() {
            @Override
            public boolean isEqual(String o1, String o2) {

                String match1 = RegexUtil.getMatch("[a-z_ ,]{1,100}", o1);
                String match2 = RegexUtil.getMatch("[a-z_ ,]{1,100}", o2);
                if(StringUtils.isBlank(match1) ){
                    return false;
                }
                return match1.equals(match2);
            }
        });

        posPair = ReplaceUtil
                .getPos(listInfo.getFullList(), "<insert id=\"add\"", "</insert>", new MapperCondition());
        listInfo.setPos(posPair);
        listInfo.setNewSegments(genAddMethod(onePojoInfo));
        ReplaceUtil.merge(listInfo, new EqualCondition<String>() {
            @Override
            public boolean isEqual(String o1, String o2) {
                String match1 = RegexUtil.getMatch("test=(.*)</if>", o1);
                String match2 = RegexUtil.getMatch("test=(.*)</if>", o2);
                if(StringUtils.isBlank(match1) ){
                    return false;
                }
                return  match1.equals(match2);
            }
        });

        fileInfo.setNewLines(listInfo.getFullList());

        posPair = ReplaceUtil
                .getPos(listInfo.getFullList(), "<insert id=\"adds\"", "</insert>", new MapperCondition());
        listInfo.setPos(posPair);
        listInfo.setNewSegments(genAddsMethod(onePojoInfo));
        ReplaceUtil.merge(listInfo, new EqualCondition<String>() {
            @Override
            public boolean isEqual(String o1, String o2) {
                String match1 = RegexUtil.getMatch("#\\{pojo.(.*)\\}", o1);
                String match2 = RegexUtil.getMatch("#\\{pojo.(.*)\\}", o2);
                if(StringUtils.isBlank(match1) ){
                    return false;
                }
                return  match1.equals(match2);
            }
        });

        fileInfo.setNewLines(listInfo.getFullList());

        posPair = ReplaceUtil
                .getPos(listInfo.getFullList(), "<update id=\"update\">", "</update>", new MapperCondition());
        listInfo.setPos(posPair);
        listInfo.setNewSegments(genUpdateMethod(onePojoInfo,expand));
        ReplaceUtil.merge(listInfo, new EqualCondition<String>() {
            @Override
            public boolean isEqual(String o1, String o2) {
                String match1 = RegexUtil.getMatch("(.*)pojo.(.*)", o1);
                String match2 = RegexUtil.getMatch("(.*)pojo.(.*)", o2);
                if(StringUtils.isBlank(match1) ){
                    return false;
                }
                return  match1.equals(match2);
            }
        });
        fileInfo.setNewLines(listInfo.getFullList());

        posPair = ReplaceUtil
                .getPos(listInfo.getFullList(), "<select id=\"query\"", "</select>", new MapperCondition());
        listInfo.setPos(posPair);
        listInfo.setNewSegments(genQueryMethod(onePojoInfo,expand));
        ReplaceUtil.merge(listInfo, new EqualCondition<String>() {
            @Override
            public boolean isEqual(String o1, String o2) {
                String match1 = RegexUtil.getMatch("(.*)pojo.(.*)", o1);
                String match2 = RegexUtil.getMatch("(.*)pojo.(.*)", o2);
                if(StringUtils.isBlank(match1) ){
                    return false;
                }
                return  match1.equals(match2);
            }
        });
        fileInfo.setNewLines(listInfo.getFullList());

        posPair = ReplaceUtil
                .getPos(listInfo.getFullList(), "<select id=\"queryUseStatement\"", "</select>", new MapperCondition());
        listInfo.setPos(posPair);
        listInfo.setNewSegments(genQueryUseStatementMethod(onePojoInfo,expand));
        ReplaceUtil.merge(listInfo, new EqualCondition<String>() {
            @Override
            public boolean isEqual(String o1, String o2) {
                String match1 = RegexUtil.getMatch("(.*)pojo.(.*)", o1);
                String match2 = RegexUtil.getMatch("(.*)pojo.(.*)", o2);
                if(StringUtils.isBlank(match1) ){
                    return false;
                }
                return  match1.equals(match2);
            }
        });
        fileInfo.setNewLines(listInfo.getFullList());
        //        newLines.addAll(getMapperHeader(onePojoInfo));
//            newLines.add("<mapper namespace=\"" + onePojoInfo.getDaoPackage() + ".Dao\">");
//            newLines.add("");
//            newLines.add("    <!--auto generated Code-->");
//            newLines.addAll(genBaseResultMap(onePojoInfo));
//            newLines.add("    <!--auto generated Code-->");
//            newLines.addAll(genAllColumn(onePojoInfo));
//            newLines.add("    <!--auto generated Code-->");
//            newLines.addAll(genAddMethod(onePojoInfo));
//            newLines.add("    <!--auto generated Code-->");
//            newLines.add(genAddsMethod(fieldTypeMap, fieldList));
//            newLines.add("    <!--auto generated Code-->");
//            newLines.add(genUpdateMethod(fieldTypeMap, fieldList));
//            newLines.add("    <!--auto generated Code-->");
//            newLines.add(genQueryMethod(fieldTypeMap, fieldList));
//            newLines.add("    <!--auto generated Code-->");
//            newLines.add(genDeleteMethod(fieldTypeMap, fieldList));
//            newLines.add("</mapper>");
    }
    
    public static List<String> getMapperHeader(OnePojoInfo onePojoInfo) {
        List<String> retList = Lists.newArrayList();
        retList.add( "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        retList.add( "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >") ;
        retList.add("<mapper namespace=\"" + onePojoInfo.getDaoPackage() +"."+ onePojoInfo.getPojoName() + "Dao\">");
        retList.add(StringUtils.EMPTY);
        retList.add("<!--auto generated Code-->");
        retList.add(ONE_RETRACT+ "<resultMap id=\"BaseResultMap\" type=\""+onePojoInfo.getPojoPackage()+"\">");
        retList.add(ONE_RETRACT+"</resultMap>");

        retList.add(StringUtils.EMPTY);
        retList.add("<!--auto generated Code-->");
        retList.add(ONE_RETRACT+ "<sql id=\"all_column\">");
        retList.add(ONE_RETRACT+"</sql>");

        retList.add(StringUtils.EMPTY);
        retList.add("<!--auto generated Code-->");
        retList.add(ONE_RETRACT+ "<insert id=\"add\">");
        retList.add(ONE_RETRACT+"</insert>");

        retList.add(StringUtils.EMPTY);
        retList.add("<!--auto generated Code-->");
        retList.add(ONE_RETRACT+ "<insert id=\"adds\">");
        retList.add(ONE_RETRACT+"</insert>");

        retList.add(StringUtils.EMPTY);
        retList.add("<!--auto generated Code-->");
        retList.add(ONE_RETRACT+ "<update id=\"update\">");
        retList.add(ONE_RETRACT+"</update>");

        retList.add(StringUtils.EMPTY);
        retList.add("<!--auto generated Code-->");
        retList.add(ONE_RETRACT+ "<select id=\"query\" resultMap=\"BaseResultMap\">");
        retList.add(ONE_RETRACT+"</select>");

        retList.add(StringUtils.EMPTY);
        retList.add("<!--auto generated Code-->");
        retList.add(ONE_RETRACT+ "<select id=\"queryUseStatement\" statementType=\"STATEMENT\" resultMap=\"BaseResultMap\">");
        retList.add(ONE_RETRACT+"</select>");

        retList.add("<!--auto generated Code-->");
        retList.add(ONE_RETRACT+ "<delete id=\"delete\">");
        retList.add(ONE_RETRACT+"</delete>");
        retList.add("</mapper>");
        return retList;
    }

    private static List<String> genBaseResultMap(OnePojoInfo onePojoInfo){
        List<String> retList = Lists.newArrayList();
        retList.add(ONE_RETRACT+ "<resultMap id=\"BaseResultMap\" type=\""+onePojoInfo.getPojoPackage()+"\">");
        for (PojoFieldInfo fieldInfo : onePojoInfo.getPojoFieldInfos()) {
            String fieldName = fieldInfo.getFieldName();
            retList.add(String.format("%s<result column=\"%s\" property=\"%s\"/>",
                    TWO_RETRACT,GenCodeUtil.getUnderScore(fieldName),fieldName));
        }
        retList.add(ONE_RETRACT+"</resultMap>");
        return retList;

    }

    private static List<String> genAllColumn(OnePojoInfo onePojoInfo) {

        List<String> retList = Lists.newArrayList();
        retList.add( ONE_RETRACT + "<sql id=\"all_column\">");
        int index = 0;
        for (PojoFieldInfo fieldInfo : onePojoInfo.getPojoFieldInfos()) {
            String s = TWO_RETRACT + getUnderScore(fieldInfo.getFieldName()) +COMMA ;
            if(index == onePojoInfo.getPojoFieldInfos().size() - 1){
                s = s.replace(COMMA, StringUtils.EMPTY);
            }
            retList.add(s);
            index ++;
        }
        retList.add(ONE_RETRACT + "</sql>");
        return retList;

    }

    private static List<String> genAddMethod(OnePojoInfo onePojoInfo) {
        List<String> retList = Lists.newArrayList();
        String tableName = GenCodeUtil.getUnderScore(onePojoInfo.getPojoClassSimpleName());
        retList.add( ONE_RETRACT + "<insert id=\"add\">");
        retList.add(TWO_RETRACT + "INSERT INTO " + tableName + "(");
        int index = 0;
        for (PojoFieldInfo fieldInfo : onePojoInfo.getPojoFieldInfos()) {
            String fieldName = fieldInfo.getFieldName();
            String s = TWO_RETRACT +  String.format("<if test=\"%s != null\"> %s, </if>"
                ,fieldName,getUnderScore(fieldName));
            if(index == onePojoInfo.getPojoFieldInfos().size() - 1){
                s = s.replace(COMMA, StringUtils.EMPTY);
            }
            retList.add(s);
            index ++;
        }
        index = 0;
        retList.add(TWO_RETRACT + ")VALUES(");
        for (PojoFieldInfo fieldInfo : onePojoInfo.getPojoFieldInfos()) {
            String fieldName = fieldInfo.getFieldName();
            String s = TWO_RETRACT + String.format("<if test=\"%s != null\"> #{%s}, </if>"
                ,fieldName,fieldName);
            if(index == onePojoInfo.getPojoFieldInfos().size() - 1){
                s = s.replace(COMMA, StringUtils.EMPTY);
            }
            retList.add(s);
            index ++;
        }
        retList.add(TWO_RETRACT + ")");
        retList.add(ONE_RETRACT + "</insert>");
        return retList;

    }

        private static List<String> genAddsMethod(OnePojoInfo onePojoInfo) {
        List<String> retList = Lists.newArrayList();
        String tableName = GenCodeUtil.getUnderScore(onePojoInfo.getPojoClassSimpleName());
        retList.add( ONE_RETRACT + "<insert id=\"adds\">");
        retList.add(TWO_RETRACT + "INSERT INTO " + tableName + "(");
        retList.add(TWO_RETRACT + "<include refid=\"all_column\"/>");
        retList.add(TWO_RETRACT + ")VALUES");
        retList.add(TWO_RETRACT + "<foreach collection=\"pojos\" item=\"pojo\" index=\"index\" separator=\",\">");
        retList.add(THREE_RETRACT + "(");
        int index = 0;
        for (PojoFieldInfo fieldInfo : onePojoInfo.getPojoFieldInfos()) {
            String fieldName = fieldInfo.getFieldName();
            String s = THREE_RETRACT +  String.format("#{pojo.%s},",fieldName);
            if(index == onePojoInfo.getPojoFieldInfos().size() - 1){
                s = s.replace(COMMA, StringUtils.EMPTY);
            }
            retList.add(s);
            index ++;
        }
        retList.add(THREE_RETRACT + ")");
        retList.add(TWO_RETRACT + "</foreach>");
        retList.add(ONE_RETRACT + "</insert>");
        return retList;

    }


    private static List<String> genUpdateMethod(OnePojoInfo onePojoInfo, Boolean expand) {
        List<String> retList = Lists.newArrayList();
        String tableName = GenCodeUtil.getUnderScore(onePojoInfo.getPojoClassSimpleName());
        retList.add( ONE_RETRACT + "<update id=\"update\">");
        retList.add(TWO_RETRACT + "UPDATE " + tableName );
        retList.add(TWO_RETRACT + "<set>" );
        int index = 0;
        for (PojoFieldInfo fieldInfo : onePojoInfo.getPojoFieldInfos()) {
            String fieldName = fieldInfo.getFieldName();
            String testCondition = THREE_RETRACT +  String.format("<if test=\"pojo.%s != null\">",fieldName);
            String updateField =  String.format("%s = #{pojo.%s},",getUnderScore(fieldName), fieldName);
            if(index == onePojoInfo.getPojoFieldInfos().size() - 1){
                updateField = updateField.replace(COMMA, StringUtils.EMPTY);
            }
            index ++;
            if(expand){
                retList.add(testCondition);
                retList.add(FOUR_RETRACT + updateField);
                retList.add(THREE_RETRACT + "</if>");
            }else{
                retList.add(testCondition + " " + updateField  + " </if>");
            }

        }
        retList.add(TWO_RETRACT + "</set>");
        retList.add(TWO_RETRACT + " WHERE id = #{pojo.id}");
        retList.add(TWO_RETRACT + "<if test=\"optimistic == 'true'\">");
        retList.add(THREE_RETRACT + "AND version = #{pojo.version}");
        retList.add(TWO_RETRACT + "</if>");
        retList.add(ONE_RETRACT + "</update>");
        return retList;
    }


    private static List<String> genQueryMethod(OnePojoInfo onePojoInfo, Boolean expand) {
        List<String> retList = Lists.newArrayList();
        String tableName = GenCodeUtil.getUnderScore(onePojoInfo.getPojoClassSimpleName());
        retList.add( ONE_RETRACT + "<select id=\"query\" resultMap=\"BaseResultMap\">");
        retList.add(TWO_RETRACT + "SELECT id, <include refid=\"all_column\"/>"  );
        retList.add(TWO_RETRACT + "FROM " + tableName  );
        retList.add(TWO_RETRACT + "WHERE id != -1 ");

        for (PojoFieldInfo fieldInfo : onePojoInfo.getPojoFieldInfos()) {
            String fieldName = fieldInfo.getFieldName();
            String testCondition = TWO_RETRACT +  String.format("<if test=\"pojo.%s != null\">",fieldName);
            String updateField =  String.format("AND %s = #{pojo.%s}",getUnderScore(fieldName), fieldName);
            if(expand){
                retList.add(testCondition);
                retList.add(THREE_RETRACT + updateField);
                retList.add(TWO_RETRACT + "</if>");
            }else{
                retList.add(testCondition + " " + updateField + " " + "</if>");
            }

        }
        retList.add(TWO_RETRACT + "LIMIT #{withLimit}");
        retList.add(ONE_RETRACT + "</select>");
        return retList;
    }

        private static List<String> genQueryUseStatementMethod(OnePojoInfo onePojoInfo, Boolean expand) {
        List<String> retList = Lists.newArrayList();
        String tableName = GenCodeUtil.getUnderScore(onePojoInfo.getPojoClassSimpleName());
        retList.add( ONE_RETRACT + "<select id=\"queryUseStatement\" statementType=\"STATEMENT\" resultMap=\"BaseResultMap\">");
        retList.add(TWO_RETRACT + "SELECT"  );
        retList.add(TWO_RETRACT + "<if test=\"option.queryCount == 'true'\"> COUNT(1) AS id </if>"  );
        retList.add(TWO_RETRACT + "<if test=\"option.queryCount != 'true'\"> id, <include refid=\"all_column\"/></if>" );
        retList.add(TWO_RETRACT + "FROM " + tableName  );
        retList.add(TWO_RETRACT + "WHERE id != -1 ");

        for (PojoFieldInfo fieldInfo : onePojoInfo.getPojoFieldInfos()) {
            String fieldName = fieldInfo.getFieldName();
            String testCondition = TWO_RETRACT +  String.format("<if test=\"pojo.%s != null\">",fieldName);
            String updateField =  String.format("AND %s = #{pojo.%s}",getUnderScore(fieldName), fieldName);
            if(expand){
                retList.add(testCondition);
                retList.add(THREE_RETRACT + updateField);
                retList.add(TWO_RETRACT + "</if>");
            }else{
                retList.add(testCondition + " " + updateField + " " + "</if>");
            }
        }
        retList.add(TWO_RETRACT + "${option.whereConditions}");
        retList.add(TWO_RETRACT + "LIMIT ${option.limitCondition}");
        retList.add(ONE_RETRACT + "</select>");
        return retList;
    }

    public static void main(String[] args) {
//        Pattern day3DataPattern = Pattern.compile("var dataSK = (.*)");
        String match = RegexUtil.getMatch("(.*)pojo.(.*)",
                "refund_finish_time = #{pojo.refundFinishTime},");
        System.out.println(match);

    }

}
