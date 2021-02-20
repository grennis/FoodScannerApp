import fetch from 'node-fetch';

const edamamURL = 'https://api.edamam.com/api/food-database/v2/parser?';

export function getProductInfo(config, upc) {
  const url = edamamURL + new URLSearchParams({
    app_id: config.edamam.id,
    app_key: config.edamam.key,
    upc: upc
  });

  return fetch(url)
    .then(response => response.json())
    .then(json => processProduct(upc, json));
}

export function processProduct(upc, product) {
  if (!product || !product.hints || !product.hints.length || !product.hints[0] || !product.hints[0].food) {
    console.info(`No food in response for ${ upc }`);
    return null;
  }

  console.info(`Got UPC ${ upc } from edamam`);
  return transformResult(product.hints[0].food);
}

export function transformResult(food) {
  const ingredients = (food.foodContentsLabel || '')
    .replace(/\(/g, ';')
    .replace(/\)/g, ';')
    .split(';')
    .map(s => s.trim())
    .map(s => s.replace(/\.$/, "")) // Remove trailing period
    .filter(s => s.length > 0);

  return {
    id: food.foodId,
    brand: food.brand,
    label: food.label,
    image: food.image,
    ingredients: ingredients
  }
}
