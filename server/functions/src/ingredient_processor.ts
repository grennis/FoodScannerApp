import {LEVEL_UNKNOWN, ingredientLevels} from "./ingredients";

const ignoreWords = ["OIL", "ACID", "FLOUR", "STARCH", "BEAN"];

export function findIn(search, items) {
    const searchUpper = search.toUpperCase();

    // First try to find exact match
    if (items.findIndex(item => item.toUpperCase() === searchUpper) >= 0) {
        return true;
    }

    // Break into words and try to find matching word
    const searchWords = searchUpper.split(' ').filter(word => !ignoreWords.includes(word));

    for (const item of items) {
        const itemsWords = item.toUpperCase().split(' ');

        for (const searchWord of searchWords) {
            if (itemsWords.findIndex(word => word === searchWord) >= 0) {
                return true;
            }
        }
    }

    return false;
}

export function getLevel(ingredient) {
    for (const ingredientLevel of ingredientLevels) {
        if (findIn(ingredient, ingredientLevel.ingredients)) {
            return ingredientLevel.level;
        }
    }

    return LEVEL_UNKNOWN;
}

export function processIngredients(strings) {
    return strings.map(str => ({
        text: str,
        level: getLevel(str),
    }));
}

export function processProduct(upc, product) {
    return {
        upc: upc,
        brand: product.brand ?? null,
        label: product.label ?? null,
        imageURL: product.image ?? null,
        ingredients: processIngredients(product.ingredients)
    }
}
