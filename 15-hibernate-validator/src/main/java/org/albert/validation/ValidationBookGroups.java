package org.albert.validation;

import jakarta.validation.groups.Default;

public interface ValidationBookGroups
{
    interface Post extends Default {}
    interface PostForService extends Default {}
}
