package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.EducationLevel
import com.example.data.model.SampleEduData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("EduPlay", appName)
  }

  @Test
  fun `verify sample education data loaded properly`() {
    assertTrue(SampleEduData.flashcards.isNotEmpty())
    assertTrue(SampleEduData.storyPages.isNotEmpty())
    assertTrue(SampleEduData.dragDropPairs.isNotEmpty())
    assertTrue(SampleEduData.quizQuestions.isNotEmpty())
    assertTrue(SampleEduData.technicalGuides.isNotEmpty())
    assertNotNull(EducationLevel.valueOf("KINDERGARTEN"))
    assertNotNull(EducationLevel.valueOf("PRIMARY"))
    assertNotNull(EducationLevel.valueOf("SECONDARY"))
    assertNotNull(EducationLevel.valueOf("VOCATIONAL"))
  }
}

