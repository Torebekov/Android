package com.example.movieproject.presentation.view_model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movieproject.FakeRepository
import com.example.movieproject.domain.model.Movie
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsCollectionContaining.hasItem
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.regex.Matcher

class MovieViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MovieViewModel
    private lateinit var fakeRepo: FakeRepository

    @Before
    fun setUp(){
        fakeRepo = FakeRepository()
        viewModel = MovieViewModel(fakeRepo)
    }

    @Test
    fun fetchRepoList_data(){
        val movie = Movie(12,12.1,12,false,"youtube.com",true,"123","Englis","","Hello",12.1,"","",0)
        val movie1 = Movie(12,12.1,12,false,"youtube.com",true,"123","Englis","","Hello",12.1,"","",0)
        val movie2 = Movie(12,12.1,12,false,"youtube.com",true,"123","Englis","","Hello",12.1,"","",0)
        val data = listOf<Movie>(movie,movie1,movie2)
        fakeRepo.insertData(movie)
        val result = viewModel.fetchRepoList()
        MatcherAssert.assertThat(result.value, hasItem(movie))
    }

}