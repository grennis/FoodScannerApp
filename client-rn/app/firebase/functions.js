import functions from '@react-native-firebase/functions';

export function fetchIngredients(upc, store) {
  console.info('Fetch ' + upc);

  const func = functions().httpsCallable('processScan');

  return func({ "upc": upc, "store": store })
    .then(response => ({
        ...response.data,
        loadingState: 'loaded'
    }))
    .catch(error => {
      return { loadingState: 'error' };
    });
}
