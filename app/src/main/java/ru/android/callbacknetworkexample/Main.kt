import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.android.callbacknetworkexample.api.Post
import ru.android.callbacknetworkexample.api.RetrofitClient
import ru.android.callbacknetworkexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fetchButton.setOnClickListener {
            fetchData()
        }
    }

    private fun fetchData() {
        val apiService = RetrofitClient.instance
        val call = apiService.getPost()

        call.enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    val post = response.body()
                    binding.resultTextView.text = post?.title
                } else {
                    Toast.makeText(applicationContext, "Error fetching data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Toast.makeText(applicationContext, "Network error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
