package utils

trait DatabaseConfig extends Config{
  val driver = slick.driver.PostgresDriver

  lazy val dbConfig: slick.basic.DatabaseConfig[slick.jdbc.JdbcProfile] = slick.basic.DatabaseConfig.forConfig[slick.jdbc.JdbcProfile]("database")
  lazy val db = dbConfig.db
}