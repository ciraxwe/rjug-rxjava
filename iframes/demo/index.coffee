# Constants
# -----------------------------------------------------------------------------
SCREEN_WIDTH = window.innerWidth
SCREEN_HEIGHT = window.innerHeight

VIEW_ANGLE = 45
ASPECT = SCREEN_WIDTH / SCREEN_HEIGHT
NEAR = 1
FAR = 1000
MS = 1000
SECOND = 60

TEXT_MATERIAL = new THREE.MeshFaceMaterial [
  new THREE.MeshPhongMaterial(color: 0xFF0000, shading: THREE.FlatShading),
  new THREE.MeshPhongMaterial(color: 0x880000, shading: THREE.SmoothShading)
]

FONT_SETTINGS =
  font: "droid serif", weight: "bold", size: 0.3, height: 0.1

# Model
# -----------------------------------------------------------------------------

# Character
class Character
  constructor: (@id, @name, @thumbnail, @comic) ->
    texture = THREE.ImageUtils.loadTexture @thumbnail
    material = new THREE.MeshLambertMaterial map: texture, side: THREE.DoubleSide, transparent: true
    @mesh = new THREE.Mesh new THREE.PlaneGeometry(1, 1.3, 4, 4), material
    @tl = new TimelineLite(paused: true).to @mesh.position, 0.3, z: '+=0.5'
    @selected = false

  select: -> if (@selected = !@selected) then @tl.play() else @tl.reverse()

# Comic
class Comic
  constructor: (@title, @thumbnail, @price) ->
    texture = THREE.ImageUtils.loadTexture @thumbnail
    material = new THREE.MeshLambertMaterial map: texture, side: THREE.DoubleSide, transparent: true
    @mesh = new THREE.Mesh new THREE.PlaneGeometry(1, 1.4, 1, 1), material
    @priceMesh = new THREEx.Text "$#{@price}", _.extend({}, FONT_SETTINGS, size: 0.1, height: 0), TEXT_MATERIAL
    @priceMesh.position.y = -0.9

# Game
class Game
  constructor: (@scene, @camera, @domEvents) ->
    zoom = 13
    @characters = []
    @selectedCharacters = []
    @cameraTl = new TimelineLite(paused: true).to @camera.position, 0.5, z: "+=#{zoom}", ease: Back.easeInOut
    @comicsGroup = new THREE.Object3D()
    @comicsGroup.position.z = zoom + 1
    @charactersGroup = new THREE.Object3D()
    @charactersGroup.position.x -= 1.2
    @charactersGroup.position.y -= 1.5

    # Add ons
    text = new THREEx.Text 'Add Ons', _.extend({}, FONT_SETTINGS, height: 0.2), TEXT_MATERIAL
    text.position.y = 0.8
    text.position.z = .4
    @comicsGroup.add text

  text: ->
    select = new THREEx.Text 'Select', FONT_SETTINGS, TEXT_MATERIAL
    select.position.x = 1.2
    select.position.y = 3.5
    select.position.z = 1
    @charactersGroup.add select

    your = new THREEx.Text 'your', FONT_SETTINGS, TEXT_MATERIAL
    your.position.y = -.8
    your.position.z = 1
    your.position.x = .5
    your.rotation.y -= 0.2
    @charactersGroup.add your

    player = new THREEx.Text 'player', FONT_SETTINGS, TEXT_MATERIAL
    player.position.y = -.8
    player.position.x = 1.8
    player.position.z = 1
    player.rotation.y += 0.2
    @charactersGroup.add player

  start: ->
    $.get 'index.json', (data) =>
      json = _.shuffle eval data
      @characters = for c in json
        new Character(c.id, c.name, c.thumbnail, new Comic(c.comic.title, c.comic.thumbnail, c.comic.price))

      # X, Y for each characters
      matrix = [[0, 0], [0, 1.5], [0, 3], [1.2, 0], [1.2, 1.5], [1.2, 3], [2.4, 0], [2.4, 1.5], [2.4, 3]]
      positions = ({x: p[0], y: p[1]} for p in matrix)

      i = 0
      for p in positions
        mesh = @characters[i].mesh
        mesh.position.x = p.x
        mesh.position.y = p.y

        if p.x == 0
          mesh.rotation.y -= 0.2
        else if p.x > 1.2
          mesh.rotation.y += 0.2
        else
          mesh.position.z += 0.2

        i++

      _.each @characters, (m) =>
        @charactersGroup.add m.mesh
        @domEvents.addEventListener m.mesh, 'click', =>
          this.select m
        , false

      this.text()
      @scene.add @charactersGroup

      document.addEventListener 'keypress', (event) =>
        if event.which == 'a'.charCodeAt(0) and @selectedCharacters.length > 0
          @cameraTl.play()
          @scene.remove @charactersGroup
          this.comics()
        else if event.which == 'b'.charCodeAt(0)
          @cameraTl.reverse()
          @scene.add @charactersGroup
          this.reset()
      , false

    this

  reset: ->
    c.select() for c in @selectedCharacters
    @selectedCharacters = []

  select: (c) ->
    @selectedCharacters.push c
    c.select()

  comics: ->
    # Clean first
    @scene.remove @comicsGroup
    @comicsGroup.remove m.comic.mesh for m in @characters
    @comicsGroup.remove m.comic.priceMesh for m in @characters

    # Add again
    x = 0
    for m in @selectedCharacters
      do (m) =>
        m.comic.mesh.position.x = x * 1.2
        m.comic.mesh.rotation.y = -1
        m.comic.priceMesh.position.x = m.comic.mesh.position.x
        @comicsGroup.add m.comic.mesh
        @comicsGroup.add m.comic.priceMesh
        x++

    comicsPos = (m.comic.mesh.position for m in @selectedCharacters)
    comicsRot = (m.comic.mesh.rotation for m in @selectedCharacters)
    pricePos = (m.comic.priceMesh.position for m in @selectedCharacters)

    TweenMax.staggerTo comicsPos, 0.2, x: "-=#{(@selectedCharacters.length - 1) * 0.6}", 0.2
    TweenMax.staggerTo comicsRot, 0.5, y: 0, 0.2
    TweenMax.staggerTo pricePos, 0.2, x: "-=#{(@selectedCharacters.length - 1) * 0.6}", 0.2

    @scene.add @comicsGroup

# ThreeJS
# -----------------------------------------------------------------------------

# Camera
window.scene = new THREE.Scene()
camera = new THREE.PerspectiveCamera VIEW_ANGLE, ASPECT, NEAR, FAR
camera.position.z = 7
camera.lookAt scene.position

# Light
dirLight = new THREE.DirectionalLight 0xffffff, 1
dirLight.position.set(0, 0, 20).normalize()
scene.add dirLight

# Renderer
renderer = if Detector.webgl then new THREE.WebGLRenderer antialias: true else new THREE.CanvasRenderer()
renderer.setClearColor new THREE.Color(0x111111), 1
renderer.setSize SCREEN_WIDTH, SCREEN_HEIGHT
onRenderFcts = []

# mouse
mouse = {}
window.addEventListener 'mousemove', (ev) ->
  mouse.x = ev.clientX / window.innerWidth
  mouse.y = ev.clientY / window.innerHeight
, false

onRenderFcts.push () ->
  if mouse.x and mouse.y
    camera.position.x = Math.sin 0.5 * Math.PI * (mouse.x - 0.5)
    camera.position.y = Math.sin 0.5 * Math.PI * (mouse.y - 0.5)
    camera.lookAt scene.position

# Extensions
THREEx.WindowResize renderer, camera
THREEx.FullScreen.bindKey charCode: 'm'.charCodeAt 0
domEvents = new THREEx.DomEvents camera, renderer.domElement

engine = new ParticleEngine()
engine.setValues Examples.fireflies
engine.initialize()

onRenderFcts.push (dt) -> engine.update dt * 0.5
window.game = new Game(scene, camera, domEvents).start()

# Render loop
lastTimeMsec = 0
onRenderFcts.push () -> renderer.render scene, camera

animate = (nowMsec) ->
  requestAnimationFrame animate
  lastTimeMsec = lastTimeMsec || nowMsec - MS / SECOND
  deltaMsec = Math.min 200, nowMsec - lastTimeMsec
  lastTimeMsec = nowMsec

  onRenderFct deltaMsec / MS, nowMsec / MS for onRenderFct in onRenderFcts

document.body.appendChild renderer.domElement
requestAnimationFrame animate
