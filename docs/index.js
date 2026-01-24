class DocViewer {
    constructor() {
        // path to documentation files
        this.docsPath = 'documentation/';
        this.init();
    }

    async init() {
        // documentation structure - folders and files
        const structure = {
            "GETTING_STARTED.md": "Getting Started",
            "core": {
                 "CAMERA.md": "Camera",
                 "MAP_LOADER.md": "MapLoader",
                 "SCENES.md": "Scenes"
            },
            "audio": {
                "SOUND.md": "Sound",
                "AUDIO_MANAGER.md": "AudioManager"
            },
            "collision": {
                "HITBOX.md": "Hitbox"
            },
            "entity": {
                "ENTITY.md": "Entity",
                "ENTITY_MANAGER.md": "EntityManager"
            },
            "input": {
                "KEYBOARD.md": "Keyboard",
                "MOUSE.md": "Mouse"
            },
            "graphics": {
                "COLOR.md": "Color",
                "DRAW_CONTEXT.md": "DrawContext",
                "objects": {
                    "FONT.md": "Font",
                    "TEXT.md": "Text",
                    "TEXTURE.md": "Texture",
                    "TILEMAP.md": "TileMap",
                    "TILESET.md": "Tileset",
                }
            }
        };

        // render the navigation tree
        this.renderTree(structure);
        this.setupEvents();
        this.loadFromHash();
    }

    // render the navigation tree structure
    renderTree(structure) {
        const container = document.getElementById('doc-tree');
        container.innerHTML = '';

        // recursive function to render tree items
        const renderItems = (items, path = '', parentElement = container) => {
            // sort keys alphabetically for consistent order
            const sortedKeys = Object.keys(items).sort();

            for (const key of sortedKeys) {
                const value = items[key];
                const isFolder = typeof value === 'object';
                const fullPath = path ? `${path}/${key}` : key;
                const displayName = isFolder ? key : value;

                // create tree item element
                const item = document.createElement('div');
                item.className = `tree-item ${isFolder ? 'folder' : 'file'}`;
                if (!isFolder) item.dataset.path = fullPath;

                // create icon element
                const icon = document.createElement('span');
                icon.className = 'tree-icon';

                // create text element
                const text = document.createElement('span');
                text.className = 'tree-text';
                text.textContent = displayName;

                // assemble the item
                item.appendChild(icon);
                item.appendChild(text);
                parentElement.appendChild(item);

                if (isFolder) {
                    // folder-specific setup
                    const arrow = document.createElement('span');
                    arrow.className = 'folder-arrow';
                    arrow.textContent = '[-]'; // expanded state
                    icon.appendChild(arrow);

                    // container for child elements
                    const childDiv = document.createElement('div');
                    childDiv.className = 'tree-children';
                    childDiv.style.display = 'block'; // start expanded

                    // toggle folder expand/collapse
                    const toggle = (e) => {
                        e.stopPropagation();
                        const hidden = childDiv.style.display === 'none';
                        childDiv.style.display = hidden ? 'block' : 'none';
                        arrow.textContent = hidden ? '[-]' : '[+]'; // change arrow direction
                    };

                    // attach toggle to both icon and text
                    icon.onclick = toggle;
                    text.onclick = toggle;

                    // recursively render children
                    renderItems(value, fullPath, childDiv);
                    parentElement.appendChild(childDiv);
                } else {
                    // file-specific setup
                    icon.textContent = '📄';
                    item.onclick = (e) => {
                        // load file when clicked (but not on arrow)
                        if (e.target !== icon && !e.target.classList.contains('folder-arrow')) {
                            this.loadFile(fullPath);
                        }
                    };
                }
            }
        };

        // start rendering from root
        renderItems(structure);
    }

    // load and display a markdown file
    async loadFile(path) {
        // remove active class from all items
        document.querySelectorAll('.tree-item.active').forEach(i => {
            i.classList.remove('active');
        });

        // add active class to current item
        const activeItem = document.querySelector(`[data-path="${path}"]`);
        if (activeItem) {
            activeItem.classList.add('active');

            // expand all parent folders to ensure visibility
            let parent = activeItem.parentElement;
            while (parent && parent.classList.contains('tree-children')) {
                const folder = parent.previousElementSibling;
                if (folder && folder.classList.contains('folder')) {
                    const arrow = folder.querySelector('.folder-arrow');
                    if (arrow) {
                        parent.style.display = 'block'; // expand
                        arrow.textContent = '[-]'; // set to expanded arrow
                    }
                }
                parent = parent.parentElement;
            }
        }

        // show loading state
        const container = document.getElementById('markdown-container');
        container.innerHTML = '<div class="loading">loading...</div>';

        try {
            // fetch the markdown file
            const response = await fetch(`${this.docsPath}${path}`);
            if (!response.ok) throw new Error();

            // convert markdown to html
            const text = await response.text();
            const html = marked.parse(text);

            // display the content
            container.innerHTML = html;

            // update url hash for bookmarking
            window.location.hash = path;

            // apply syntax highlighting if hljs is available
            if (window.hljs) {
                document.querySelectorAll('pre code').forEach(block => {
                    hljs.highlightElement(block);
                });
            }

        } catch {
            // show error if loading fails
            container.innerHTML = '<div class="error">failed to load</div>';
        }
    }

    // set up event listeners
    setupEvents() {
        // load file when hash changes (back/forward buttons)
        window.addEventListener('hashchange', () => this.loadFromHash());
    }

    // load file based on url hash
    loadFromHash() {
        const hash = window.location.hash.substring(1);
        if (hash && hash.endsWith('.md')) {
            this.loadFile(hash);
        }
    }
}

// initialize the viewer when dom is ready
document.addEventListener('DOMContentLoaded', () => {
    window.viewer = new DocViewer();
});