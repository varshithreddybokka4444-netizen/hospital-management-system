const API_URL = "http://localhost:8080/Appointments";

const appointmentTableBody = document.getElementById("appointmentTableBody");
const appointmentCount = document.getElementById("appointmentCount");

const appointmentModal = document.getElementById("appointmentModal");
const appointmentForm = document.getElementById("appointmentForm");

const addAppointmentBtn = document.getElementById("addAppointmentBtn");
const cancelBtn = document.getElementById("cancelBtn");
const closeBtn = document.getElementById("closeModal");

const searchAppointment = document.getElementById("searchAppointment");

const appointmentPublicId = document.getElementById("appointmentPublicId");
const patientPublicId = document.getElementById("patientPublicId");
const doctorPublicId = document.getElementById("doctorPublicId");
const appointmentTime = document.getElementById("appointmentTime");
const appointmentReason = document.getElementById("appointmentReason");

let appointments = [];
let filteredAppointments = [];

async function loadAppointments() {
    try {
        const response = await fetch(API_URL);

        appointments = await response.json();

        filteredAppointments = [...appointments];

        renderAppointments(filteredAppointments);

    } catch (error) {
        console.error(error);
    }
}

function renderAppointments(data) {

    appointmentTableBody.innerHTML = "";

    appointmentCount.textContent = `Showing ${data.length} Appointments`;

    data.forEach(appointment => {

        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${appointment.publicId}</td>
            <td>${appointment.patient?.name ?? ""}</td>
            <td>${appointment.doctor?.name ?? ""}</td>
            <td>${new Date(appointment.appointmentTime).toLocaleString()}</td>
            <td>${appointment.reason}</td>

            <td>

                <button
                    class="edit-btn"
                    onclick="editAppointment('${appointment.publicId}')">

                    <i class="fa-solid fa-pen"></i>

                </button>

                <button
                    class="delete-btn"
                    onclick="deleteAppointment('${appointment.publicId}')">

                    <i class="fa-solid fa-trash"></i>

                </button>

            </td>
        `;

        appointmentTableBody.appendChild(row);

    });

}

searchAppointment.addEventListener("input", () => {

    const value = searchAppointment.value.toLowerCase();

    filteredAppointments = appointments.filter(appointment =>

        appointment.publicId.toLowerCase().includes(value) ||

        appointment.patient?.name.toLowerCase().includes(value) ||

        appointment.doctor?.name.toLowerCase().includes(value)

    );

    renderAppointments(filteredAppointments);

});

addAppointmentBtn.addEventListener("click", () => {

    appointmentForm.reset();

    appointmentPublicId.value = "";

    document.getElementById("modalTitle").textContent = "Book Appointment";

    appointmentModal.style.display = "flex";

});

cancelBtn.addEventListener("click", closeModal);
closeBtn.addEventListener("click", closeModal);

function closeModal() {

    appointmentModal.style.display = "none";

}
function editAppointment(publicId) {

    const appointment = appointments.find(a => a.publicId === publicId);

    if (!appointment) return;

    appointmentPublicId.value = appointment.publicId;

    patientPublicId.value = appointment.patient?.publicId || "";

    doctorPublicId.value = appointment.doctor?.publicId || "";

    appointmentTime.value = appointment.appointmentTime.slice(0, 16);

    appointmentReason.value = appointment.reason;

    document.getElementById("modalTitle").textContent = "Edit Appointment";

    appointmentModal.style.display = "flex";

}

appointmentForm.addEventListener("submit", async (e) => {

    e.preventDefault();

    const data = {

        patientPublicId: patientPublicId.value,

        doctorPublicId: doctorPublicId.value,

        appointmentTime: appointmentTime.value,

        reason: appointmentReason.value

    };

    try {

        if (appointmentPublicId.value) {

            await fetch(`${API_URL}/${appointmentPublicId.value}`, {

                method: "PUT",

                headers: {

                    "Content-Type": "application/json"

                },

                body: JSON.stringify(data)

            });

        } else {

            await fetch(API_URL, {

                method: "POST",

                headers: {

                    "Content-Type": "application/json"

                },

                body: JSON.stringify(data)

            });

        }

        closeModal();

        loadAppointments();

    } catch (error) {

        console.error(error);

    }

});

async function deleteAppointment(publicId) {

    if (!confirm("Are you sure you want to cancel this appointment?")) return;

    try {

        await fetch(`${API_URL}/${publicId}`, {

            method: "DELETE"

        });

        loadAppointments();

    } catch (error) {

        console.error(error);

    }

}

window.addEventListener("click", (e) => {

    if (e.target === appointmentModal) {

        closeModal();

    }

});

document.addEventListener("keydown", (e) => {

    if (e.key === "Escape") {

        closeModal();

    }

});

loadAppointments();