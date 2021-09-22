package com.enki.database

import com.enki.models.UserInfo
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import com.impossibl.postgres.jdbc.*

object User : Table() {
    val login = varchar("login", 10)
    val displayName = varchar("displayname", 100)
    val isStaff = bool("staff?")
    val profileImage = varchar("image_url", 300)
    val correctionPoints = integer("correction_point")
    val wallet = integer("wallet")

    override val primaryKey = PrimaryKey(login, name = "INTRA_User_ID")
}

fun databaseConnection(userInfo: UserInfo) {
    val psqlDB: String = System.getenv("PSQL_DB") ?: ""
    val psqlUser: String = System.getenv("PSQL_USER") ?: ""
    val psqlPassword: String = System.getenv("PSQL_PASSWORD") ?: ""

    Database.connect("jdbc:pgsql://localhost:5432/$psqlDB", driver = "com.impossibl.postgres.jdbc.PGDriver", user = psqlUser, password = psqlPassword)

    transaction {
        addLogger(StdOutSqlLogger)

        SchemaUtils.create (User)
        User.deleteWhere { User.login eq userInfo.login }

        User.insert {
                it[login] = userInfo.login
                it[displayName] = userInfo.displayName
                it[isStaff] = userInfo.isStaff
                it[profileImage] = userInfo.profileImage
                it[correctionPoints] = userInfo.correctionPoints
                it[wallet] = userInfo.wallet
            }
    }
}
