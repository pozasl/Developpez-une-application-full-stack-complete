/**
 * MDD OpenAPI definition
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
import { Author } from './author';
import { Reply } from './reply';
import { Topic } from './topic';


export interface Post { 
    id?: string;
    title?: string;
    topic?: Topic;
    content?: string;
    author?: Author;
    replies?: Reply[];
    created_at?: string;
}

