const state = {
    token: localStorage.getItem("gp_token") || "",
    username: localStorage.getItem("gp_username") || "",
    role: localStorage.getItem("gp_role") || "",
    profesionEditId: null,
    personaEditId: null,
    profesiones: []
};

const elements = {
    statusBox: document.querySelector("#status-box"),
    sessionBadge: document.querySelector("#session-badge"),
    sessionUser: document.querySelector("#session-user"),
    sessionRole: document.querySelector("#session-role"),
    publicPersonas: document.querySelector("#public-personas"),
    profesionesList: document.querySelector("#profesiones-list"),
    personasList: document.querySelector("#personas-list"),
    personaProfesion: document.querySelector("#persona-profesion"),
    loginForm: document.querySelector("#login-form"),
    profesionForm: document.querySelector("#profesion-form"),
    personaForm: document.querySelector("#persona-form")
};

function setStatus(message, data = null) {
    const lines = [message];
    if (data) {
        lines.push(JSON.stringify(data, null, 2));
    }
    elements.statusBox.textContent = lines.join("\n\n");
}

function setSession(token, username, role) {
    state.token = token || "";
    state.username = username || "";
    state.role = role || "";

    localStorage.setItem("gp_token", state.token);
    localStorage.setItem("gp_username", state.username);
    localStorage.setItem("gp_role", state.role);

    if (!state.token) {
        localStorage.removeItem("gp_token");
        localStorage.removeItem("gp_username");
        localStorage.removeItem("gp_role");
    }

    elements.sessionUser.textContent = state.username || "-";
    elements.sessionRole.textContent = state.role || "-";
    elements.sessionBadge.textContent = state.token ? "Autenticado" : "Sin autenticar";
    elements.sessionBadge.className = `badge ${state.token ? "badge-active" : "badge-idle"}`;
}

async function apiFetch(path, options = {}, requiresAuth = false) {
    const headers = new Headers(options.headers || {});

    if (!headers.has("Content-Type") && options.body) {
        headers.set("Content-Type", "application/json");
    }

    if (requiresAuth) {
        if (!state.token) {
            throw new Error("Debes iniciar sesion para usar esta operacion.");
        }
        headers.set("Authorization", `Bearer ${state.token}`);
    }

    const response = await fetch(path, {
        ...options,
        headers
    });

    const contentType = response.headers.get("content-type") || "";
    const payload = contentType.includes("application/json")
        ? await response.json().catch(() => null)
        : await response.text().catch(() => "");

    if (!response.ok) {
        if (response.status === 401 || response.status === 403) {
            setSession("", "", "");
        }
        throw new Error(extractErrorMessage(payload) || `Error HTTP ${response.status}`);
    }

    return payload;
}

function extractErrorMessage(payload) {
    if (!payload) {
        return "";
    }
    if (typeof payload === "string") {
        return payload;
    }
    if (payload.message) {
        return payload.message;
    }
    if (payload.errors) {
        return Object.entries(payload.errors)
            .map(([field, message]) => `${field}: ${message}`)
            .join(" | ");
    }
    return "";
}

function resetProfesionForm() {
    state.profesionEditId = null;
    document.querySelector("#profesion-id").value = "";
    document.querySelector("#profesion-nombre").value = "";
    document.querySelector("#profesion-descripcion").value = "";
}

function resetPersonaForm() {
    state.personaEditId = null;
    document.querySelector("#persona-id").value = "";
    document.querySelector("#persona-documento").value = "";
    document.querySelector("#persona-nombre").value = "";
    document.querySelector("#persona-apellido").value = "";
    document.querySelector("#persona-edad").value = "";
    document.querySelector("#persona-salario").value = "";
    document.querySelector("#persona-profesion").value = "";
}

function renderProfesiones(profesiones) {
    state.profesiones = profesiones;
    elements.profesionesList.innerHTML = "";
    elements.personaProfesion.innerHTML = '<option value="">Selecciona una profesion</option>';

    if (!profesiones.length) {
        elements.profesionesList.innerHTML = '<div class="card"><p>No hay profesiones cargadas.</p></div>';
        return;
    }

    profesiones.forEach((profesion) => {
        const option = document.createElement("option");
        option.value = profesion.id;
        option.textContent = `${profesion.nombre} (#${profesion.id})`;
        elements.personaProfesion.appendChild(option);

        const card = document.createElement("article");
        card.className = "card";
        card.innerHTML = `
            <h3>${profesion.nombre}</h3>
            <p>${profesion.descripcion}</p>
            <p><strong>ID:</strong> ${profesion.id}</p>
            <div class="card-actions">
                <button type="button" data-action="edit-profesion" data-id="${profesion.id}">Editar</button>
                <button type="button" data-action="delete-profesion" data-id="${profesion.id}" class="button-danger">Eliminar</button>
            </div>
        `;
        elements.profesionesList.appendChild(card);
    });
}

function renderPersonas(personas, container) {
    container.innerHTML = "";

    if (!personas.length) {
        container.innerHTML = '<div class="card"><p>No hay personas para mostrar.</p></div>';
        return;
    }

    personas.forEach((persona) => {
        const card = document.createElement("article");
        card.className = "card";
        card.innerHTML = `
            <h3>${persona.nombre} ${persona.apellido}</h3>
            <p><strong>Documento:</strong> ${persona.documento}</p>
            <p><strong>Edad:</strong> ${persona.edad}</p>
            <p><strong>Salario:</strong> ${Number(persona.salario).toLocaleString("es-CO", { style: "currency", currency: "COP" })}</p>
            <p><strong>Profesion:</strong> ${persona.profesion.nombre}</p>
            ${container === elements.personasList ? `
                <div class="card-actions">
                    <button type="button" data-action="edit-persona" data-id="${persona.id}">Editar</button>
                    <button type="button" data-action="delete-persona" data-id="${persona.id}" class="button-danger">Eliminar</button>
                </div>
            ` : ""}
        `;
        container.appendChild(card);
    });
}

async function loadPublicPersonas() {
    const personas = await apiFetch("/api/persona/public");
    renderPersonas(personas, elements.publicPersonas);
    setStatus("Vista publica cargada.", personas);
}

async function loadProfesiones() {
    const profesiones = await apiFetch("/api/profesion", {}, true);
    renderProfesiones(profesiones);
    setStatus("Profesiones cargadas.", profesiones);
}

async function loadPersonas() {
    const personas = await apiFetch("/api/persona", {}, true);
    renderPersonas(personas, elements.personasList);
    setStatus("Personas cargadas.", personas);
}

async function loadPersonasByAge() {
    const edad = document.querySelector("#persona-edad-filtro").value;
    const personas = await apiFetch(`/api/persona/edad?edad=${encodeURIComponent(edad)}`, {}, true);
    renderPersonas(personas, elements.personasList);
    setStatus(`Personas filtradas por edad mayor que ${edad}.`, personas);
}

async function handleLogin(event) {
    event.preventDefault();

    const username = document.querySelector("#username").value.trim();
    const password = document.querySelector("#password").value;

    try {
        const data = await apiFetch("/auth/login", {
            method: "POST",
            body: JSON.stringify({ username, password })
        });
        setSession(data.token, data.username, data.role);
        setStatus("Sesion iniciada correctamente.", data);
        await Promise.all([loadProfesiones(), loadPersonas(), loadPublicPersonas()]);
    } catch (error) {
        setStatus(error.message);
    }
}

function handleLogout() {
    setSession("", "", "");
    resetProfesionForm();
    resetPersonaForm();
    elements.profesionesList.innerHTML = "";
    elements.personasList.innerHTML = "";
    setStatus("Sesion cerrada.");
}

async function handleProfesionSubmit(event) {
    event.preventDefault();

    const body = {
        nombre: document.querySelector("#profesion-nombre").value.trim(),
        descripcion: document.querySelector("#profesion-descripcion").value.trim()
    };

    const isEditing = Boolean(state.profesionEditId);
    const path = isEditing ? `/api/profesion/${state.profesionEditId}` : "/api/profesion";
    const method = isEditing ? "PUT" : "POST";

    try {
        const data = await apiFetch(path, {
            method,
            body: JSON.stringify(body)
        }, true);
        resetProfesionForm();
        await loadProfesiones();
        setStatus(`Profesion ${isEditing ? "actualizada" : "creada"} correctamente.`, data);
    } catch (error) {
        setStatus(error.message);
    }
}

async function handlePersonaSubmit(event) {
    event.preventDefault();

    const body = {
        documento: document.querySelector("#persona-documento").value.trim(),
        nombre: document.querySelector("#persona-nombre").value.trim(),
        apellido: document.querySelector("#persona-apellido").value.trim(),
        edad: Number(document.querySelector("#persona-edad").value),
        salario: Number(document.querySelector("#persona-salario").value),
        profesionId: Number(document.querySelector("#persona-profesion").value)
    };

    const isEditing = Boolean(state.personaEditId);
    const path = isEditing ? `/api/persona/${state.personaEditId}` : "/api/persona";
    const method = isEditing ? "PUT" : "POST";

    try {
        const data = await apiFetch(path, {
            method,
            body: JSON.stringify(body)
        }, true);
        resetPersonaForm();
        await loadPersonas();
        setStatus(`Persona ${isEditing ? "actualizada" : "creada"} correctamente.`, data);
    } catch (error) {
        setStatus(error.message);
    }
}

async function handleCardActions(event) {
    const button = event.target.closest("button[data-action]");
    if (!button) {
        return;
    }

    const { action, id } = button.dataset;
    const numericId = Number(id);

    try {
        if (action === "edit-profesion") {
            const profesion = state.profesiones.find((item) => item.id === numericId);
            if (!profesion) {
                throw new Error("Profesion no encontrada en la grilla actual.");
            }
            state.profesionEditId = profesion.id;
            document.querySelector("#profesion-id").value = profesion.id;
            document.querySelector("#profesion-nombre").value = profesion.nombre;
            document.querySelector("#profesion-descripcion").value = profesion.descripcion;
            setStatus("Formulario de profesion cargado para edicion.", profesion);
            return;
        }

        if (action === "delete-profesion") {
            await apiFetch(`/api/profesion/${numericId}`, { method: "DELETE" }, true);
            await loadProfesiones();
            setStatus("Profesion eliminada.");
            return;
        }

        if (action === "edit-persona") {
            const persona = await apiFetch(`/api/persona/${numericId}`, {}, true);
            state.personaEditId = persona.id;
            document.querySelector("#persona-id").value = persona.id;
            document.querySelector("#persona-documento").value = persona.documento;
            document.querySelector("#persona-nombre").value = persona.nombre;
            document.querySelector("#persona-apellido").value = persona.apellido;
            document.querySelector("#persona-edad").value = persona.edad;
            document.querySelector("#persona-salario").value = persona.salario;
            document.querySelector("#persona-profesion").value = persona.profesion.id;
            setStatus("Formulario de persona cargado para edicion.", persona);
            return;
        }

        if (action === "delete-persona") {
            await apiFetch(`/api/persona/${numericId}`, { method: "DELETE" }, true);
            await loadPersonas();
            setStatus("Persona eliminada.");
        }
    } catch (error) {
        setStatus(error.message);
    }
}

function bindEvents() {
    elements.loginForm.addEventListener("submit", handleLogin);
    elements.profesionForm.addEventListener("submit", handleProfesionSubmit);
    elements.personaForm.addEventListener("submit", handlePersonaSubmit);

    document.querySelector("#logout-btn").addEventListener("click", handleLogout);
    document.querySelector("#load-public-btn").addEventListener("click", () => loadPublicPersonas().catch((error) => setStatus(error.message)));
    document.querySelector("#load-profesiones-btn").addEventListener("click", () => loadProfesiones().catch((error) => setStatus(error.message)));
    document.querySelector("#load-personas-btn").addEventListener("click", () => loadPersonas().catch((error) => setStatus(error.message)));
    document.querySelector("#load-age-filter-btn").addEventListener("click", () => loadPersonasByAge().catch((error) => setStatus(error.message)));
    document.querySelector("#refresh-all-btn").addEventListener("click", async () => {
        try {
            await loadPublicPersonas();
            if (state.token) {
                await Promise.all([loadProfesiones(), loadPersonas()]);
            }
        } catch (error) {
            setStatus(error.message);
        }
    });
    document.querySelector("#reset-profesion-btn").addEventListener("click", resetProfesionForm);
    document.querySelector("#reset-persona-btn").addEventListener("click", resetPersonaForm);

    elements.profesionesList.addEventListener("click", handleCardActions);
    elements.personasList.addEventListener("click", handleCardActions);
}

async function bootstrap() {
    bindEvents();
    setSession(state.token, state.username, state.role);
    await loadPublicPersonas();

    if (state.token) {
        try {
            await Promise.all([loadProfesiones(), loadPersonas()]);
            setStatus("Sesion restaurada desde almacenamiento local.");
        } catch (error) {
            setStatus(error.message);
        }
    }
}

bootstrap().catch((error) => setStatus(error.message));
