package com.enki.database

import com.enki.models.UserInfo
import com.impossibl.postgres.jdbc.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object User : Table() {
    val login = varchar("login", 10)
    val displayName = varchar("displayname", 100)
    val isStaff = bool("staff?")
    val profileImage = varchar("image_url", 300)
    val correctionPoints = integer("correction_point")
    val wallet = integer("wallet")

    override val primaryKey = PrimaryKey(login, name = "INTRA_USER_ID")
}

fun databaseConnection(userInfo: UserInfo) {
    val psqlDB: String = System.getenv("POSTGRES_DB") ?: ""
    val psqlUser: String = System.getenv("POSTGRES_USER") ?: ""
    val psqlPassword: String = System.getenv("POSTGRES_PASSWORD") ?: ""

    Database.connect(
            "jdbc:pgsql://localhost:5432/$psqlDB",
            driver = "com.impossibl.postgres.jdbc.PGDriver",
            user = psqlUser,
            password = psqlPassword
    )

    transaction {
        addLogger(StdOutSqlLogger)
        SchemaUtils.create(User)
    }
    val userExists = transaction {
        addLogger(StdOutSqlLogger)
        User.select { User.login eq userInfo.login }.toList()
    }
    transaction {
        addLogger(StdOutSqlLogger)
        if (userExists.size == 0) {
            User.insert {
                it[login] = userInfo.login
                it[displayName] = userInfo.displayName
                it[isStaff] = userInfo.isStaff
                it[profileImage] = userInfo.profileImage
                it[correctionPoints] = userInfo.correctionPoints
                it[wallet] = userInfo.wallet
            }
        } else {
            User.update({ User.login eq userInfo.login }) {
                it[login] = userInfo.login
                it[displayName] = userInfo.displayName
                it[isStaff] = userInfo.isStaff
                it[profileImage] = userInfo.profileImage
                it[correctionPoints] = userInfo.correctionPoints
                it[wallet] = userInfo.wallet
            }
        }
    }
}
