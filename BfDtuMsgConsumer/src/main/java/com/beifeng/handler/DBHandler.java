package com.beifeng.handler;

import com.beifeng.DataUtil.StringUtil;
import com.beifeng.config.DBProperties;

import java.sql.*;
import java.sql.Connection;
import java.util.Map;
/**
 * Created by 胡志洁 on 2016/6/23.
 */
public class DBHandler {

    private Connection connection;

    public DBHandler(){

    }

    private void initConnect() throws SQLException {
        //Class.forName("org.postgresql.Driver");
        this.connection = DriverManager.getConnection(DBProperties.jdbc,DBProperties.username, DBProperties.password);
    }

    private void SetPreparedStatementParam(PreparedStatement pst,DTUMsg msg) throws SQLException {
        pst.setTimestamp(1, msg.getTime());
        pst.setString(2, msg.getType());
        pst.setFloat(3, StringUtil.parseFloat(msg.getMsgFiled(0)));
        pst.setFloat(4, StringUtil.parseFloat(msg.getMsgFiled(1)));
        pst.setFloat(5, StringUtil.parseFloat(msg.getMsgFiled(2)));
        pst.setFloat(6, StringUtil.parseFloat(msg.getMsgFiled(3)));
        pst.setFloat(7, StringUtil.parseFloat(msg.getMsgFiled(4)));
        pst.setFloat(8, StringUtil.parseFloat(msg.getMsgFiled(5)));
        pst.setFloat(9, StringUtil.parseFloat(msg.getMsgFiled(6)));
        pst.setFloat(10, StringUtil.parseFloat(msg.getMsgFiled(7)));
        pst.setFloat(11, StringUtil.parseFloat(msg.getMsgFiled(80)));
        pst.setFloat(12, StringUtil.parseInt(msg.getMsgFiled(9)));
        pst.setFloat(13, StringUtil.parseInt(msg.getMsgFiled(10)));
        pst.setFloat(14, StringUtil.parseInt(msg.getMsgFiled(11)));
        pst.setFloat(15, StringUtil.parseInt(msg.getMsgFiled(12)));
        pst.setFloat(16, StringUtil.parseInt(msg.getMsgFiled(13)));
        pst.setFloat(17, StringUtil.parseInt(msg.getMsgFiled(14)));
        pst.setFloat(18, StringUtil.parseInt(msg.getMsgFiled(15)));
        pst.setFloat(19, StringUtil.parseInt(msg.getMsgFiled(16)));
        pst.setFloat(20, StringUtil.parseFloat(msg.getMsgFiled(17)));
        pst.setFloat(21, StringUtil.parseFloat(msg.getMsgFiled(18)));
        pst.setFloat(22, StringUtil.parseFloat(msg.getMsgFiled(19)));
        pst.setFloat(23, StringUtil.parseFloat(msg.getMsgFiled(20)));
        pst.setString(24, msg.getDtuId());
    }

    public boolean insert(String str) {
        DTUMsg msg = this.parse(str);
        if(msg == null){
            return false;
        }
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            this.initConnect();
            PreparedStatement pst2 = this.connection.prepareStatement("INSERT INTO T_WATCH_LIST2( DT, TP, F01, F02, F03, F04, F05, F06, F07, F08, F09, N01, N02, N03, N04, N05, N06, N07, N08, F10, F11, F12, F13,DTU) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)");
            this.SetPreparedStatementParam(pst2,msg);
            /*
            pst2.setTimestamp(1, msg.getTime());
            pst2.setString(2, msg.getType());
            pst2.setFloat(3, StringUtil.parseFloat(msg.getMsgFiled(0)));
            pst2.setFloat(4, StringUtil.parseFloat(msg.getMsgFiled(1)));
            pst2.setFloat(5, StringUtil.parseFloat(msg.getMsgFiled(2)));
            pst2.setFloat(6, StringUtil.parseFloat(msg.getMsgFiled(3)));
            pst2.setFloat(7, StringUtil.parseFloat(msg.getMsgFiled(4)));
            pst2.setFloat(8, StringUtil.parseFloat(msg.getMsgFiled(5)));
            pst2.setFloat(9, StringUtil.parseFloat(msg.getMsgFiled(6)));
            pst2.setFloat(10, StringUtil.parseFloat(msg.getMsgFiled(7)));
            pst2.setFloat(11, StringUtil.parseFloat(msg.getMsgFiled(80)));
            pst2.setFloat(12, StringUtil.parseInt(msg.getMsgFiled(9)));
            pst2.setFloat(13, StringUtil.parseInt(msg.getMsgFiled(10)));
            pst2.setFloat(14, StringUtil.parseInt(msg.getMsgFiled(11)));
            pst2.setFloat(15, StringUtil.parseInt(msg.getMsgFiled(12)));
            pst2.setFloat(16, StringUtil.parseInt(msg.getMsgFiled(13)));
            pst2.setFloat(17, StringUtil.parseInt(msg.getMsgFiled(14)));
            pst2.setFloat(18, StringUtil.parseInt(msg.getMsgFiled(15)));
            pst2.setFloat(19, StringUtil.parseInt(msg.getMsgFiled(16)));
            pst2.setFloat(20, StringUtil.parseFloat(msg.getMsgFiled(17)));
            pst2.setFloat(21, StringUtil.parseFloat(msg.getMsgFiled(18)));
            pst2.setFloat(22, StringUtil.parseFloat(msg.getMsgFiled(19)));
            pst2.setFloat(23, StringUtil.parseFloat(msg.getMsgFiled(20)));
            pst2.setString(24, msg.getDtuId());
            */
            pst2.execute();
            //connection.commit();

            PreparedStatement pst3 = connection.prepareStatement("UPDATE T_WATCH_NOW SET DT=?, TP=?, F01=?, F02=?, F03=?, F04=?, F05=?, F06=?, F07=?, F08=?, F09=?, N01=?, N02=?, N03=?, N04=?, N05=?, N06=?, N07=?, N08=?, F10=?, F11=?, F12=?, F13=? WHERE DTU=?");
            this.SetPreparedStatementParam(pst3,msg);
            int res = pst3.executeUpdate();
            if(res <=0) {
                PreparedStatement pst4 = connection.prepareStatement("INSERT INTO T_WATCH_NOW(DT, TP, F01, F02, F03, F04, F05, F06, F07, F08, F09, N01, N02, N03, N04, N05, N06, N07, N08, F10, F11, F12, F13, DTU) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                this.SetPreparedStatementParam(pst4,msg);
                pst4.execute();
            }

            connection.close();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }


    }
    public DTUMsg parse(String str){
        try{
            String[] data = str.split(">");
            if(data.length < 2){
                return null;
            }

            String[] msgHead = data[0].split("TYPE:");
            if(msgHead.length < 2){
                return null;
            }
            else{

                String[] idHead = msgHead[0].split("TIME:");
                String dtuId = idHead[0];
                String date = idHead[1];
                String type = msgHead[1];

                if(data[1].startsWith("+")) {
                    data[1] = data[1].substring(1);
                }
                String[] msgBody = data[1].split("\\+");

                DTUMsg DTUMsg = new DTUMsg(dtuId,date,type,msgBody);

                return DTUMsg;
            }


        }
        catch(Exception e){
            System.out.println("DTU_MSG is illegal");
            return null;
        }
    }
    /*
    public Map<String,String> parse(String str){


        try{
            Map<String,String> map = new HashMap<String,String>();

            System.out.println("the parsed string is : " + str);
            str = str.substring(6);
            str = str.replaceAll("&&", ";");
            str = str.replaceAll(",", ";");
            String[] aa = str.split(";");
            for (int i = 0; i < aa.length; i++) {
                String[] bb = aa[i].split("=");
                if(bb.length>1) {
                    System.out.println(bb[0]+"="+bb[1]);
                    map.put(bb[0], bb[1]);
                } else {
                    System.out.println(bb[0]+"=null");
                    map.put(bb[0], null);
                }
            }

            return map;
        }
        catch (Exception e){
            System.out.println("DTU_MSG is illegal");
            return new HashMap<String,String>();
        }


    }
    */
}
