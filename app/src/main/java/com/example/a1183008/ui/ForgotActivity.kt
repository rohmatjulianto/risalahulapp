package com.example.a1183008.ui

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a1183008.Api
import com.example.a1183008.LoginActivity
import com.example.a1183008.Model.ForgotPassResponse
import com.example.a1183008.R
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ForgotActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)

        val email = findViewById<EditText>(R.id.edt_email)
        val btn = findViewById<Button>(R.id.bt_submit)
        val pbar = findViewById<ProgressBar>(R.id.pb_bar)

        btn.setOnClickListener{
            if (!email.text.isEmpty()){
                getReset(pbar, email)
            }else{
                email.error = "Email belum diisi"
            }

        }
    }

    fun getReset(pbar: ProgressBar,email: EditText){
        pbar.visibility = View.VISIBLE
        val call = Api.service().getResetPass(email.text.toString())
        call.enqueue(object : Callback<ForgotPassResponse> {
            override fun onResponse(call: Call<ForgotPassResponse>, response: Response<ForgotPassResponse>) {
                if (response.body()?.status == "berhasil") {
                    pbar.visibility = View.GONE
                    showSnakBar("Reset password berhasil, silahkan periksa email anda")
                    startActivity(Intent(this@ForgotActivity, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                } else {
                    showSnakBar("Reset password gagal, silahkan ulangi kembali")
                    pbar.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<ForgotPassResponse>, t: Throwable) {
                showSnakBar(t.message.toString())
                pbar.visibility = View.GONE
            }

        })
    }

    fun showSnakBar(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}
