import firestore from '@react-native-firebase/firestore';

function scansCollection(userID) {
  return firestore()
    .collection(`users/${ userID }/scans`);
}

export function listenScans(userID, setScans) {
  scansCollection(userID)
    .orderBy("date", "desc")
    .onSnapshot(snapshot => {
      setScans(snapshot.docs.map(doc => ({
        ...doc.data(),
        id: doc.id,
        date: doc.data().date.toDate()
      })))
    });
}

