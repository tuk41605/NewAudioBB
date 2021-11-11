package edu.temple.newaudiobb

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BookListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_book_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val manager = LinearLayoutManager(activity)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        val bookViewModel = ViewModelProvider(requireActivity()).get(SelectedBookViewModel::class.java)
        val onClick : (Book) -> Unit = {
            // Update the ViewModel
                book: Book -> bookViewModel.setSelectedBook(book)
            // Inform the activity of the selection so as to not have the event replayed
            // when the activity is restarted
            (activity as BookSelectedInterface).bookSelected()
        }
        recyclerView?.layoutManager = manager
        recyclerView?.adapter = BookListAdapter(BookList, onClick)
    }

    interface BookSelectedInterface {
        fun bookSelected()
    }
}