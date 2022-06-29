package util;

import entity.User;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
* @ClassName CsvUtil
* @Author reason-llh
* @Date 2022/4/22 15:10
* @Description csv格式文件导入导出工具类
* @Version 1.0.0
**/

public class CsvUtil {

    // csv文件的Header
    private static String[] fileHeader = {"昵称", "邮箱","手机","分组",
            "头像路径","备注","住址","工作单位",
            "主页地址","邮政编码","生日","电话"
    };

    /**
    *
    * @param csvFile
    * @param content
    * @author reason-llh
    * @date 2022/4/22 15:21
    * @description 将通讯录导出为csv
    **/
    public static void write(final String csvFile,  List<String[]> content)
            throws IOException {

        BufferedWriter writer = Files.newBufferedWriter(Paths.get(csvFile));
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(fileHeader));
        for (String[] c : content) {
            csvPrinter.printRecord(Arrays.asList(c));
        }
        csvPrinter.flush();
        csvPrinter.close();
        writer.close();
    }

    /**
    *
    * @param csvFile
    * @param skipHeader
    * @return java.util.List<entity.User>
    * @author reason-llh
    * @date 2022/4/22 15:32
    * @description 将csv文件导入通讯录
    **/
    public static List<User> read(final String csvFile, boolean skipHeader)
            throws IOException {
        CSVFormat format;

        if (skipHeader) {
            // 这里显式地配置一下CSV文件的Header
            // 然后设置跳过Header（要不然读的时候会把头也当成一条记录）
            format = CSVFormat.DEFAULT.withHeader(fileHeader)
                    .withFirstRecordAsHeader().withIgnoreHeaderCase()
                    .withTrim();
        } else {
            format = CSVFormat.DEFAULT.withHeader(fileHeader).withIgnoreHeaderCase().withTrim();
        }
        Reader reader = Files.newBufferedReader(Paths.get(csvFile));

        try ( CSVParser csvParser = new CSVParser(reader, format);){
            List<CSVRecord> csvRecords = csvParser.getRecords();
            List<User> list = new ArrayList<>(csvRecords.size());
            for (CSVRecord csvRecord : csvRecords) {
                User user = new User();
                user.setNickname(csvRecord.get("昵称"));
                user.setEmail(csvRecord.get("邮箱"));
                user.setPhone(csvRecord.get("手机"));
                user.setGroup(csvRecord.get("分组"));
                user.setFilePath(csvRecord.get("头像路径"));
                user.setRemark(csvRecord.get("备注"));
                user.setAddress(csvRecord.get("住址"));
                user.setWorkUnit(csvRecord.get("工作单位"));
                user.setMainPage(csvRecord.get("主页地址"));
                user.setEmailcode(csvRecord.get("邮政编码"));
                user.setBirthday(csvRecord.get("生日"));
                user.setDh(csvRecord.get("电话"));
                list.add(user);
            }
            reader.close();
            return list;
        }
    }
}
