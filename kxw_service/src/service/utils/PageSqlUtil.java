package service.utils;
public class PageSqlUtil {
    public static String getPageSql(String strSql, int startIndex, int endIndex){
        StringBuffer sb = new StringBuffer("SELECT B.* FROM (SELECT A.*, rownum rn FROM (");
        sb.append(strSql);
        sb.append(") A WHERE rownum <=");
        sb.append(endIndex);
        sb.append(") B WHERE rn >");
        sb.append(startIndex);
        return sb.toString();
    }
}