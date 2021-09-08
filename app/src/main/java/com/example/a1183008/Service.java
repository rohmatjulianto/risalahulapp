package com.example.a1183008;

import com.example.a1183008.Model.ForgotPassResponse;
import com.example.a1183008.Model.ModelDeleteUser;
import com.example.a1183008.Model.ModelInputHaid;
import com.example.a1183008.Model.ModelKitab;
import com.example.a1183008.Model.ModelLogin;
import com.example.a1183008.Model.ModelRegister;
import com.example.a1183008.Model.ModelUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Service {
    @GET("loginapi.php")
    Call<List<ModelLogin>> getLogin(
            @Query("username") String username,
            @Query("password") String password
    );

    @FormUrlEncoded
    @POST("registeruser.php")
    Call<List<ModelRegister>> TambahRespon(
            @Field("nama") String nama,
            @Field("username") String username,
            @Field("password") String password,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("userapi.php")
    Call<List<ModelUser>> ResponUser(
            @Field("id") String id

    );
    @FormUrlEncoded
    @POST("tampilkitab.php")
    Call<List<ModelKitab>> getData(
            @Field("tanya") String tanya
    );

    @FormUrlEncoded
    @POST("userdata.php")
    Call<List<ModelUser>> getUserData(
            @Field("tanya") String tanya
    );

    @FormUrlEncoded
    @POST("delete_user.php")
    Call<List<ModelDeleteUser>> getDelete(
            @Field("id_user") String id_user
    );

    @FormUrlEncoded
    @POST("tambah_user.php")
    Call<List<ModelUser>> getAddUser(
            @Field("nama") String nama,
            @Field("username") String username,
            @Field("password") String password,
            @Field("role_id") String role_id
    );
    @FormUrlEncoded
    @POST("updateuser.php")
    Call<List<ModelUser>> getUpdateUser(
            @Field("id_user") String id_user,
            @Field("nama") String nama,
            @Field("username") String username,
            @Field("password") String password,
            @Field("role_id") String role_id
    );

    @FormUrlEncoded
    @POST("updatekitab.php")
    Call<List<ModelKitab>> getUpdateKitab(
            @Field("id_kitab") String id_kitab,
            @Field("nama_kitab") String nama_kitab,
            @Field("isi_kitab") String isi_kitab
    );

    @FormUrlEncoded
    @POST("update_userid.php")
    Call<List<ModelUser>> getUpdateId(
            @Field("username") String username,
            @Field("id_firebase") String id_firebase
    );

    @FormUrlEncoded
    @POST("tambahkitab.php")
    Call<List<ModelKitab>> getTambahKitab(
            @Field("nama_kitab") String nama_kitab,
            @Field("isi_kitab") String isi_kitab
    );

    @FormUrlEncoded
    @POST("deletekitab.php")
    Call<List<ModelDeleteUser>> getDeletKitab(
            @Field("id_kitab") String id_kitab
    );

    @FormUrlEncoded
    @POST("inputhaidapi.php")
    Call<List<ModelInputHaid>> getInputHaid(
            @Field("id_user") String id_user,
            @Field("tgl_awal") String awal,
            @Field("tgl_akhir") String akhir,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("riwayathaidapi.php")
    Call<List<ModelInputHaid>> getRiwayat(
            @Field("id_user") String id_user
    );

    @FormUrlEncoded
    @POST("forgotpass.php")
    Call<ForgotPassResponse> getResetPass(
            @Field("email") String email
    );


}
