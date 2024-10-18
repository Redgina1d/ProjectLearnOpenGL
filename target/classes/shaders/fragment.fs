#version 400 core

in vec2 fragTextureCoord;
in vec3 fragNormal;
in vec3 fragPos;
in vec3 surfaceNormal;
in vec3 toLightVector;

out vec4 fragColour;

struct Material {
	vec4 ambient;
	vec4 diffuse;
	vec4 specular;
	int hasTexture;
	float reflectance;
};

uniform sampler2D textureSampler;
uniform vec3 ambientLight;
uniform Material material;
uniform vec3 lightColour;

vec4 ambientC;
vec4 diffuseC;
vec4 specularC;

void setupColours(Material material, vec2 texCoords) {
	if(material.hasTexture == 1) {
		ambientC = texture(textureSampler, texCoords);
		diffuseC = ambientC;
		specularC = ambientC;
	} else {
		ambientC = material.ambient;
		diffuseC = material.diffuse;
		specularC = material.specular;
	}
}

void main() {

	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitLightVector = normalize(toLightVector);

	float nDot1 = dot(unitNormal, unitLightVector);
	float brightness = max(nDot1, 0.0);
	vec3 diffuse = brightness * lightColour;

	if(material.hasTexture == 1) {
		ambientC = texture(textureSampler, fragTextureCoord);
	} else {
		ambientC = material.ambient + material.specular + material.diffuse + material.reflectance;
	}

	fragColour = vec4(diffuse, 1.0) * ambientC * vec4(ambientLight, 1);
}













