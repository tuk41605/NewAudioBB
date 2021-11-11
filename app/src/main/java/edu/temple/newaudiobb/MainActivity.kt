package edu.temple.newaudiobb

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity(), BookListFragment.BookSelectedInterface{

    private lateinit var mainSearchButton: Button

    private val isSingleContainer: Boolean by lazy {
        findViewById<View>(R.id.container2) == null
    }

    private val viewModelSelected: SelectedBookViewModel by lazy {
        ViewModelProvider(this).get(SelectedBookViewModel::class.java)
    }

    private val intentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container1, BookListFragment())
                .commit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainSearchButton = findViewById(R.id.mainSearchButton)

        mainSearchButton.setOnClickListener {
            intentLauncher.launch(Intent(this, BookSearchActivity::class.java))
        }

        // If we're switching from one container to two containers
        // clear BookDetailsFragment from container1
        if (supportFragmentManager.findFragmentById(R.id.container1) is BookDetailsFragment) {
            supportFragmentManager.popBackStack()
        }

        // If this is the first time the activity is loading, go ahead and add a BookListFragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container1, BookListFragment())
                .commit()
        } else if (isSingleContainer && viewModelSelected.getSelectedBook().value != null) {
            // If activity loaded previously, there's already a BookListFragment
            // If we have a single container and a selected book, place it on top
            supportFragmentManager.beginTransaction()
                .replace(R.id.container1, BookDetailsFragment())
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit()
        }

        // If we have two containers but no BookDetailsFragment, add one to container2
        if (!isSingleContainer && supportFragmentManager.findFragmentById(R.id.container2) !is BookDetailsFragment) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container2, BookDetailsFragment())
                .commit()
        }
    }

    override fun onBackPressed() {
        viewModelSelected.setSelectedBook(null)
        super.onBackPressed()
    }

    override fun bookSelected() {
        // Perform a fragment replacement if we only have a single container
        // when a book is selected

        if (isSingleContainer) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container1, BookDetailsFragment())
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit()
        }
        else if (!isSingleContainer) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container2, BookDetailsFragment())
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit()
        }
    }
}