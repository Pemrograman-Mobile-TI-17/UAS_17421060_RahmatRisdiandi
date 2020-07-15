package com.example.RahmatRisdiandi.server;

public class BaseURL {

    public static String baseUrl = "http://192.168.100.19:5050/";

    public static String login      = baseUrl + "user/login";
    public static String register   = baseUrl + "user/registrasi";

    //buku
    public static String dataSkincare = baseUrl + "skincare/dataskincare";
    public static String editDataSkincare = baseUrl + "skincare/ubah/";
    public static String hapusData = baseUrl + "skincare/hapus/";
    public static String inputSkincare = baseUrl + "skincare/input";

}
