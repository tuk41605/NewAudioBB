package edu.temple.newaudiobb

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.squareup.picasso.Picasso

class BookDetailsFragment : Fragment() {

    private lateinit var bookTitle: TextView
    private lateinit var bookAuthor: TextView
    private lateinit var bookCover: ImageView
    private val viewModelSelected: SelectedBookViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_book_details, container, false)

        bookTitle = view.findViewById(R.id.titleTextView2)
        bookAuthor = view.findViewById(R.id.authorTextView2)
        bookCover = view.findViewById(R.id.bookCoverImageView)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelSelected.getSelectedBook().observe(requireActivity(), {updateBook(it)})
    }

    private fun updateBook(book:Book?){
        book?.run {
            bookTitle.text = title
            bookAuthor.text = author
            Picasso.get().load(coverURL).into(bookCover)
        }
    }
}