package com.mijndomein.mysql;

public class Main {
    public static void main(String[] args) throws Exception {
        MySQLAccess mysql = new MySQLAccess();
        mysql.connectDataBase();
    }

}