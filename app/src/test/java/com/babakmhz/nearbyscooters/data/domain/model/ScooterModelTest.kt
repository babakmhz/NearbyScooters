package com.babakmhz.nearbyscooters.data.domain.model

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ScooterModelTest {

    @Test
    fun `test scooters with same id should be equal`() {
        val id = "dummyId"
        val item1 = Scooter(
            0, 0, "", id, 0.0, 0.0, "",
            null, null, null, "", "", ""
        )
        val item2 = item1.copy(id = id)

        Assert.assertTrue(item1 == item2)
        Assert.assertEquals(item1.hashCode(), item2.hashCode())
    }

    @Test
    fun `test scooters with different id should not be equal`() {
        val id = "dummyId"
        val item1 = Scooter(
            0, 0, "", id, 0.0, 0.0, "",
            null, null, null, "", "", ""
        )
        val item2 = item1.copy(id = id+"something")

        Assert.assertFalse(item1 == item2)
        Assert.assertNotEquals(item1.hashCode(), item2.hashCode())
    }
}