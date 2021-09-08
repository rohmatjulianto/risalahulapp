package com.example.a1183008;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionPreference {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME="simpan_session";
    Context context;
    int private_mode = 0;

    public SessionPreference(Context context){
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, private_mode);
        editor = pref.edit();
    }

    public void setLogin (String login){
        editor.putString("login", login);
        editor.commit();
    }
    public String getLogin(){
        return pref.getString("login", "false");
    }

    public void setAwal (int awal){
        editor.putInt("awal", awal);
        editor.commit();
    }
    public int getAwal(){
        return pref.getInt("awal", 0);
    }

    public void setAkhir (int akhir){
        editor.putInt("akhir", akhir);
        editor.commit();
    }
    public int getAkhir(){
        return pref.getInt("akhir", 0);
    }



    public void setAwalTgl (String awalTgl){
        editor.putString("awalTgl", awalTgl);
        editor.commit();
    }
    public String getAwalTgl(){
        return pref.getString("awalTgl", "0");
    }

    public void setAkhirTgl (String akhirTgl){
        editor.putString("akhirTgl", akhirTgl);
        editor.commit();
    }
    public String getAkhirTgl(){
        return pref.getString("akhirTgl", "0");
    }

    public void setRole (String role){
        editor.putString("role", role);
        editor.commit();
    }
    public String getRole(){
        return pref.getString("role", "false");
    }

    public void setUsername (String username){
        editor.putString("username", username);
        editor.commit();
    }
    public String getUsername(){
        return pref.getString("username", "null");
    }

    public void setTglAwalo (String tglawalo){
        editor.putString("tglawalo", tglawalo);
        editor.commit();
    }
    public String getTglAwalo(){
        return pref.getString("tglawalo", "null");
    }


}
