package repository

object CodeGen extends App {
  slick.codegen.SourceCodeGenerator.run(
    "slick.jdbc.PostgresProfile",
    "org.postgresql.Driver",
    "jdbc:postgresql://localhost/internship?user=srdjan&password=admin",
    "C:\\Users\\Public\\internship\\srdjan-djuric-praksa\\back\\app",
    "repository", None, None, true, false)
}
