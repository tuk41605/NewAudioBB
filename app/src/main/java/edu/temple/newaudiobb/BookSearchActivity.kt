package edu.temple.newaudiobb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import org.json.JSONException
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

class BookSearchActivity : AppCompatActivity() {
    lateinit var searchEditText: EditText

    private val volleyQueue: RequestQueue by lazy { Volley.newRequestQueue(this) }

    private val bookList by lazy { BookList }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_search)

        searchEditText = findViewById(R.id.searchBar)
        val searchButton = findViewById<Button>(R.id.searchButton)
        val closeButton = findViewById<Button>(R.id.closeButton)
        val emptyBook : Book = Book(0, "", "","")

        searchButton.setOnClickListener {

            val url = "https://kamorris.com/lab/cis3515/search.php?term=" + searchEditText.text

            if (bookList.size() != 0) {
                bookList.clear()
            }

            volleyQueue.add(JsonArrayRequest(Request.Method.GET, url, null,
                {
                    try {
                        for (i in 0 until it.length()) {
                            val retrieved = it.getJSONObject(i)
                            val id = retrieved.getString("id").toInt()
                            val title = retrieved.getString("title")
                            val author = retrieved.getString("author")
                            val coverURL = retrieved.getString("cover_url")
                            bookList.add(Book(id, title, author, coverURL))
                        }
                    } catch (e: JSONException) {
                        Toast.makeText(this, "JSONException error", Toast.LENGTH_SHORT).show()
                    }
                },
                {
                    Toast.makeText(this, "Volley error", Toast.LENGTH_SHORT).show()
                }))

            volleyQueue.addRequestFinishedListener<JsonArrayRequest> {
                if (bookList.size() == 0) {
                    bookList.add(emptyBook)
                    setResult(RESULT_OK, Intent().putExtra("RESULTS", bookList))
                } else
                    setResult(RESULT_OK, Intent().putExtra("RESULTS", bookList))
                finish()
            }
        }

        closeButton.setOnClickListener {
            finish()
        }
    }

    override fun onStop() {
        super.onStop()
        volleyQueue.cancelAll(this)
    }
}