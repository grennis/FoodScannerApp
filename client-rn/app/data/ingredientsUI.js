import { LEVEL_MILD, LEVEL_MODERATE, LEVEL_NONE, LEVEL_SEVERE, LEVEL_UNKNOWN } from "./ingredients";

export function getLevelName(level) {
  switch (level) {
    case LEVEL_UNKNOWN: return 'Unknown';
    case LEVEL_NONE: return 'None';
    case LEVEL_MILD: return 'Mild';
    case LEVEL_MODERATE: return 'Moderate';
    case LEVEL_SEVERE: return 'Severe';
    default: return '?';
  }
}

export function getLevelIcon(level) {
  if (level === LEVEL_NONE || level === LEVEL_UNKNOWN) {
    return 'md-thumbs-up';
  } else {
    return 'ios-warning';
  }
}

export function getLevelColor(level) {
  switch (level) {
    case LEVEL_UNKNOWN: return '#444444';
    case LEVEL_NONE: return '#009900';
    case LEVEL_MILD: return '#999900';
    case LEVEL_MODERATE: return '#DD8800';
    case LEVEL_SEVERE: return '#BB0000';
    default: return '#0000BB';
  }
}

function getLevelsText(items) {
  const levels = items.map(ingredient => ingredient.level);

  if (levels.includes(LEVEL_SEVERE)) {
    if (levels.includes(LEVEL_MODERATE)) {
      if (levels.includes(LEVEL_MILD)) {
        return 'Contains severe, moderate, and mild ingredients.';
      } else {
        return 'Contains severe and moderate ingredients.';
      }
    } else if (levels.includes(LEVEL_MILD)) {
      return 'Contains severe and mild ingredients.';
    } else {
      return 'Contains severe ingredients.';
    }
  } else if (levels.includes(LEVEL_MODERATE)) {
    if (levels.includes(LEVEL_MILD)) {
      return 'Contains moderate and mild ingredients.';
    } else {
      return 'Contains moderate ingredients.';
    }
  } else if (levels.includes(LEVEL_MILD)) {
    return 'Contains mild ingredients.';
  } else {
    return 'Contains no sensitive ingredients.';
  }
}

export function getSummary(product) {
  if (!product.ingredients || !product.ingredients.length) {
    return {
      text: 'No ingredients listed.'
    }
  }

  const max = product.ingredients.reduce((prev, current) => {
    return (prev.level > current.level) ? prev : current
  });

  return {
    icon: getLevelIcon(max?.level),
    iconColor: getLevelColor(max?.level),
    text: getLevelsText(product.ingredients),
  };
}
