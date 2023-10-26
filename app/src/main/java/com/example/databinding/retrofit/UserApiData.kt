package com.example.databinding.retrofit

class UserApiData {
    var data: DataClass? = null
    var support: SupportClass? = null

    inner class DataClass {
        var first_name: String? = null
        var last_name: String? = null
        var id: String? = null
        var email: String? = null
        var avatar: String? = null
    }

    inner class SupportClass {
        var url: String? = null
        var text: String? = null
    }
}
