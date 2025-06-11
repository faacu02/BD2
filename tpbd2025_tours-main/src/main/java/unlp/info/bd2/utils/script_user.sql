use bd2_grupo_1

db.createUser({
  user: "usuario_bd2",
  pwd: "pass_bd2",
  roles: [
    { role: "readWrite", db: "bd2_grupo_1" }
  ]
})

