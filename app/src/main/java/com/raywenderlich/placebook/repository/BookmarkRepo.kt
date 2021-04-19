package com.raywenderlich.placebook.repository

import com.raywenderlich.placebook.db.PlaceBookDatabase
import com.raywenderlich.placebook.model.Bookmark
import android.content.Context
import androidx.lifecycle.LiveData
import com.google.android.libraries.places.api.model.Place
import com.raywenderlich.placebook.R
import com.raywenderlich.placebook.db.BookmarkDao


//  Pass context object to constructor for PlaceBookDatabase instance
class BookmarkRepo(private val context: Context) {
    // 2
    private var db = PlaceBookDatabase.getInstance(context)
    private var bookmarkDao: BookmarkDao = db.bookmarkDao()
    // Hold mapping of place types to category names
    private var categoryMap: HashMap<Place.Type, String> = buildCategoryMap()
    private var allCategories: HashMap<String, Int> = buildCategories()
    val categories: List<String> get() = ArrayList(allCategories.keys)



    // Logic for adding bookmark to the repo
    fun addBookmark(bookmark: Bookmark): Long? {
        val newId = bookmarkDao.insertBookmark(bookmark)
        bookmark.id = newId
        return newId
    }
    // Create new initialized bookmark object
    fun createBookmark(): Bookmark {
        return Bookmark()
    }

    fun getLiveBookmark(bookmarkId: Long): LiveData<Bookmark> {
        val bookmark = bookmarkDao.loadLiveBookmark(bookmarkId)
        return bookmark
    }

    // Returns LiveData list of all bookmarks in repo
    val allBookmarks: LiveData<List<Bookmark>>
        get() {
            return bookmarkDao.loadAll()
        }

    fun updateBookmark(bookmark: Bookmark) {
        bookmarkDao.updateBookmark(bookmark)
    }

    fun getBookmark(bookmarkId: Long): Bookmark {
        return bookmarkDao.loadBookmark(bookmarkId)
    }

    //Builds HashMap relating place typs to category names
    private fun buildCategoryMap() : HashMap<Place.Type, String> {
        return hashMapOf(
                Place.Type.BAKERY to "Restaurant",
                Place.Type.BAR to "Restaurant",
                Place.Type.CAFE to "Restaurant",
                Place.Type.FOOD to "Restaurant",
                Place.Type.RESTAURANT to "Restaurant",
                Place.Type.MEAL_DELIVERY to "Restaurant",
                Place.Type.MEAL_TAKEAWAY to "Restaurant",
                Place.Type.GAS_STATION to "Gas",
                Place.Type.CLOTHING_STORE to "Shopping",
                Place.Type.DEPARTMENT_STORE to "Shopping",
                Place.Type.FURNITURE_STORE to "Shopping",
                Place.Type.GROCERY_OR_SUPERMARKET to "Shopping",
                Place.Type.HARDWARE_STORE to "Shopping",
                Place.Type.HOME_GOODS_STORE to "Shopping",
                Place.Type.JEWELRY_STORE to "Shopping",
                Place.Type.SHOE_STORE to "Shopping",
                Place.Type.SHOPPING_MALL to "Shopping",
                Place.Type.STORE to "Shopping",
                Place.Type.LODGING to "Lodging",
                Place.Type.ROOM to "Lodging"
        )
    }

    // Take place type and convert it to valid category - other by default
    fun placeTypeToCategory(placeType: Place.Type): String {
        var category = "Other"
        if (categoryMap.containsKey(placeType)) {
            category = categoryMap[placeType].toString()
        }
        return category
    }

    // Builds HashMap that relates category names to category resource ids
    private fun buildCategories() : HashMap<String, Int> {
        return hashMapOf(
                "Gas" to R.drawable.ic_gas,
                "Lodging" to R.drawable.ic_lodging,
                "Other" to R.drawable.ic_other,
                "Restaurant" to R.drawable.ic_restaurant,
                "Shopping" to R.drawable.ic_shopping
        )
    }

    // Public method to convert category name to category resource id
    fun getCategoryResourceId(placeCategory: String): Int? {
        return allCategories[placeCategory]
    }

    fun deleteBookmark(bookmark: Bookmark) {
        bookmark.deleteImage(context)
        bookmarkDao.deleteBookmark(bookmark)
    }
}