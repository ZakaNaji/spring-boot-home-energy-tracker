import { faker } from "@faker-js/faker";

const USER_SERVICE_BASE_URL = "http://localhost:8081";
const DEVICE_SERVICE_BASE_URL = "http://localhost:8082";

const USERS_TO_CREATE = 35;
const DEVICES_PER_USER_MIN = 4;
const DEVICES_PER_USER_MAX = 7;

const DEVICE_TYPES = [
    "AIR_CONDITIONER",
    "FRIDGE",
    "WASHING_MACHINE",
    "DISHWASHER",
    "TELEVISION",
    "WATER_HEATER",
    "MICROWAVE",
    "OVEN",
    "LAPTOP",
    "ROUTER",
    "LAMP",
    "HEATER"
];

const LOCATIONS = [
    "Living Room",
    "Kitchen",
    "Bedroom",
    "Bathroom",
    "Garage",
    "Office",
    "Dining Room",
    "Hallway"
];

function randomBoolean() {
    return Math.random() < 0.5;
}

function randomThreshold() {
    return Number(faker.number.float({ min: 100, max: 1500, fractionDigits: 1 }).toFixed(1));
}

function randomInt(min, max) {
    return faker.number.int({ min, max });
}

function buildUserPayload(index) {
    const firstName = faker.person.firstName();
    const lastName = faker.person.lastName();

    return {
        name: firstName,
        surname: lastName,
        email: `seed.user.${String(index).padStart(3, "0")}@example.com`,
        address: faker.location.streetAddress({ useFullAddress: true }),
        alertingEnabled: randomBoolean(),
        energyAlertThreshold: randomThreshold()
    };
}

function buildDevicePayload(userId) {
    const type = faker.helpers.arrayElement(DEVICE_TYPES);
    const location = faker.helpers.arrayElement(LOCATIONS);

    return {
        name: `${location} ${type.replaceAll("_", " ")}`,
        type,
        location,
        userId
    };
}

async function postJson(url, body) {
    const response = await fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(body)
    });

    const text = await response.text();
    let parsedBody = null;

    if (text) {
        try {
            parsedBody = JSON.parse(text);
        } catch {
            parsedBody = text;
        }
    }

    if (!response.ok) {
        throw new Error(
            `Request failed: ${response.status} ${response.statusText} | url=${url} | body=${JSON.stringify(body)} | response=${JSON.stringify(parsedBody)}`
        );
    }

    return parsedBody;
}

async function createUsers() {
    const createdUsers = [];

    console.log(`Creating ${USERS_TO_CREATE} users...`);

    for (let i = 1; i <= USERS_TO_CREATE; i++) {
        const payload = buildUserPayload(i);
        const createdUser = await postJson(`${USER_SERVICE_BASE_URL}/api/users`, payload);

        createdUsers.push(createdUser);

        console.log(`Created user ${i}/${USERS_TO_CREATE} -> id=${createdUser.id}, email=${createdUser.email}`);
    }

    return createdUsers;
}

async function createDevices(users) {
    let totalDevicesCreated = 0;

    console.log(`Creating devices for ${users.length} users...`);

    for (const user of users) {
        const devicesToCreate = randomInt(DEVICES_PER_USER_MIN, DEVICES_PER_USER_MAX);

        for (let i = 0; i < devicesToCreate; i++) {
            const payload = buildDevicePayload(user.id);
            const createdDevice = await postJson(`${DEVICE_SERVICE_BASE_URL}/api/devices`, payload);

            totalDevicesCreated++;

            console.log(
                `Created device #${totalDevicesCreated} -> id=${createdDevice.id}, userId=${createdDevice.userId}, type=${createdDevice.type}`
            );
        }
    }

    return totalDevicesCreated;
}

async function main() {
    try {
        console.log("Starting data seeding...");
        console.log(`User service: ${USER_SERVICE_BASE_URL}`);
        console.log(`Device service: ${DEVICE_SERVICE_BASE_URL}`);

        const users = await createUsers();
        const deviceCount = await createDevices(users);

        console.log("Seeding completed successfully.");
        console.log(`Users created: ${users.length}`);
        console.log(`Devices created: ${deviceCount}`);
    } catch (error) {
        console.error("Seeding failed.");
        console.error(error.message);
        process.exit(1);
    }
}

main();