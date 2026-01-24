# Entity Manager
`additional section`

if you don't want to update and render all entities individually and clutter your main code, 
<br>let's write a simple script to automate rendering and updating:
```kt 
class EntityManager {
    private val entities = mutableListOf<Entity>()
    
    fun add(entity: Entity) {
        entities.add(entity)
        entity.create()
    }
    
    fun updateAll(delta: Float) {
        entities.forEach { it.update(delta) }
    }
    
    fun renderAll(draw: DrawContext) {
        entities.forEach { it.render(draw) }
    }
}
```
and integrate it into the main game class:<br>
```kt 
// ...your imports...


class MyGame : Game() {
    private val entities = EntityManager()
    // usage: 
    // entities.add(YourEntity()),
    // entities.updateAll(delta),
    // entities.renderAll(draw) 
    
    // ...your code...
}
```