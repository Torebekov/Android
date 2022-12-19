package com.example.movieproject.presentation.utilities

import org.hamcrest.core.Is.`is`
import org.junit.Assert.*
import org.junit.Test

class LoginUtilTest {
    @Test
    fun `empty username returns false`() {
        val result = LoginUtil.validateLoginForm(
            "",
            "123"
        )
        assertThat(result,`is`(false))
    }

    @Test
    fun `empyt password returns false`() {
        val result = LoginUtil.validateLoginForm(
            "Philipp",
            ""
        )
        assertThat(result,`is`(false))
    }

    @Test
    fun `valid username and password returns true`() {
        val result = LoginUtil.validateLoginForm(
            "Philipp",
            "123"
        )
        assertThat(result,`is`(true))
    }

    @Test
    fun `less than 2 digit password returns false`() {
        val result = LoginUtil.validateLoginForm(
            "Philipp",
            "abcdefg5"
        )
        assertThat(result,`is`(false))
    }

}