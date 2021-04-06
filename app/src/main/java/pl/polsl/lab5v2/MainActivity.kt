package pl.polsl.lab5v2

import android.app.Activity
import android.os.Bundle


class MainActivity : Activity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(MySurface(applicationContext))
    }
}