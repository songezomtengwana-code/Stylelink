package com.ekasi.stylelink.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.ekasi.stylelink.R

class LoadingFragment : Fragment() {
    private lateinit var loadingAnimationView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_loading, container, false)
        loadingAnimationView = view.findViewById(R.id.loadingAnimationView)

        Glide.with(requireContext())
            .asGif()
            .load("https://i.ibb.co/dJ4td0f/stylelink-gif.gif")
            .into(loadingAnimationView)

        return view;
    }

}