package com.purple.hello.feature.rooms.fake

import com.purple.core.model.Member
import com.purple.core.model.Room
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object FakeFactory {
    fun makeUser(id: Long, name: String, profileUrl: String): Member {
        return Member(id, name, profileUrl)
    }

    fun makeRoom(id: Long, name: String): Room {
        return Room(id, name, makeMembers())
    }

    fun makeRooms(): Flow<List<Room>> {
        return flow {
            emit(
                listOf(
                    makeRoom(1, "AA 집"),
                    makeRoom(2, "BB 집"),
                    makeRoom(3, "CC 집"),
                    makeRoom(4, "DD 집"),
                ),
            )
        }
    }

    fun makeMembers(): List<Member> {
        return listOf(
            makeUser(
                1,
                "홍길동(자식)",
                "https://imgv3.fotor.com/images/share/Unblur-image-online-automatically-with-Fotor-AI-image-deburring-tool.jpg",
            ),
            makeUser(
                2,
                "홍동길(자식)",
                "https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8aHVtYW58ZW58MHx8MHx8&w=1000&q=80",
            ),
            makeUser(
                3,
                "홍파파(아빠)",
                "https://www.adobe.com/content/dam/cc/us/en/creativecloud/photography/discover/cut-out-an-image/thumbnail.jpeg",
            ),
            makeUser(
                4,
                "김마마(엄마)",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTP6-MfoJ0MLH3ZH7oIyNvP_PfLRoYI-ZgPeQ&usqp=CAU",
            ),
            makeUser(
                5,
                "김마마(엄마)",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTP6-MfoJ0MLH3ZH7oIyNvP_PfLRoYI-ZgPeQ&usqp=CAU",
            ),
            makeUser(
                6,
                "김마마(엄마)",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTP6-MfoJ0MLH3ZH7oIyNvP_PfLRoYI-ZgPeQ&usqp=CAU",
            ),
        )
    }
}
