import * as admin from 'firebase-admin';

const maxScansPerUser = 35;

admin.initializeApp();
const firestore = admin.firestore();

function scansCollection(userID) {
    return firestore
        .collection(`users/${ userID }/scans`);
}

export function getScans(userID) {
    return scansCollection(userID)
        .orderBy("date", "desc")
        .get()
        .then(snap => snap.docs);
}

export async function storeScan(userID, product) {
    const scans = await getScans(userID);
    const date = admin.firestore.Timestamp.fromDate(new Date());
    const existing = scans.find(s => s.get("upc") === product.upc);

    if (existing) {
        return existing.ref.set({
            date: date
        }, { merge: true });
    }

    const scan = {
        upc: product.upc,
        brand: product.brand,
        label: product.label,
        date: date
    };

    await scansCollection(userID).add(scan);

    for (const doc of scans.slice(maxScansPerUser - 1)) {
        await doc.ref.delete();
    }

    return Promise.resolve();
}
