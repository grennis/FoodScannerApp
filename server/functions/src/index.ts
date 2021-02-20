import * as functions from 'firebase-functions';
import { getProductInfo } from './scan_processor';
import { processProduct } from "./ingredient_processor";
import { storeScan } from "./scan_storage";

exports.processScan = functions.https.onCall(async (data, context) => {
  const uid = (context.auth || {}).uid;
  if (!uid) {
    return Promise.reject(new functions.https.HttpsError('permission-denied', 'Not Authorized'));
  }

  const product = await getProductInfo(functions.config(), data.upc)
      .then(p => processProduct(data.upc, p));

  if (data.store) {
    await storeScan(uid, product);
  }

  return Promise.resolve(product);
});
