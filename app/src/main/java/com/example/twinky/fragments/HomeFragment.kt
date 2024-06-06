package com.example.twinky.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.twinky.Models.MovieModel
import com.example.twinky.Models.Post
import com.example.twinky.Models.User
import com.example.twinky.R
import com.example.twinky.adapter.FollowRvAdapter
import com.example.twinky.adapter.PopularRvAdapter
import com.example.twinky.adapter.PostAdapter
import com.example.twinky.databinding.FragmentHomeBinding
import com.example.twinky.utils.FOLLOW
import com.example.twinky.utils.POST
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var postList = ArrayList<Post>()
    private lateinit var adapter: PostAdapter

    private var followList = ArrayList<User>()
    private lateinit var followAdapter: FollowRvAdapter

    private var popularList = ArrayList<MovieModel>()
    private lateinit var popularAdapter: PopularRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        adapter = PostAdapter(requireContext(), postList)
        //binding.postRv.layoutManager = LinearLayoutManager(requireContext())
        binding.postRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.postRv.adapter = adapter



        followAdapter = FollowRvAdapter(requireContext(), followList)
        binding.followRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.followRv.adapter = followAdapter



        popularAdapter = PopularRvAdapter(popularList)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.popularRv.itemAnimator = DefaultItemAnimator()
        //binding.popularRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.popularRv.layoutManager = layoutManager
        binding.popularRv.adapter = popularAdapter
        prepareMovieData()



        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW).get().addOnSuccessListener{

            var tempList = ArrayList<User>()
            followList.clear()

            for (i in it.documents){

                var user: User = i.toObject<User>()!!
                tempList.add(user)
            }
            followList.addAll(tempList)
            followAdapter.notifyDataSetChanged()

        }


        setHasOptionsMenu(true)
//        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.materialToolbar2)

        Firebase.firestore.collection(POST).get().addOnSuccessListener {

            var tempList = ArrayList<Post>()
            postList.clear()

            for (i in it.documents){

                var post: Post = i.toObject<Post>()!!
                tempList.add(post)
            }

            for (i in tempList.indices.reversed()) {
                postList.add(0, tempList[i])
            }

            //postList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }

        return binding.root


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun prepareMovieData() {

        var movie = MovieModel("Cookie together", R.drawable.cooking)
        popularList.add(movie)
        movie = MovieModel("Queen", R.drawable.queen)
        popularList.add(movie)
        movie = MovieModel("Harry Potter", R.drawable.harry_potter)
        popularList.add(movie)
        movie = MovieModel("Froggies", R.drawable.frog_item)
        popularList.add(movie)
        movie = MovieModel("Sport", R.drawable.sport)
        popularList.add(movie)
        movie = MovieModel("Taty", R.drawable.tatoo)
        popularList.add(movie)


        popularAdapter.notifyDataSetChanged()
    }
}